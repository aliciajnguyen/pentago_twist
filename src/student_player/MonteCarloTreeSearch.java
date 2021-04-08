package student_player;


import boardgame.Board;
import boardgame.Move;
//import pentago_twist.PentagoBoard;
import pentago_twist.PentagoBoardState;
import pentago_twist.PentagoMove;
//import pentago_twist.PentagoPlayer;

import java.util.ArrayList;
import java.util.Random;


/* MCST algorithm implementation:
*
* Uses MC_Node, MC_NodeState, MC_SearchTree, UC_Tree Classes
* 
* -MonteCarloSearchTree
* -- MC_Node
* --- MC_NodeState
* -- MC_SearchTree
* -- UC_Tree Classes

* External Code Sources (also in Sources Cited):
* https://www.baeldung.com/java-monte-carlo-tree-search
*
*/

public class MonteCarloTreeSearch{
    int win_score = 1; //the reward we give
    int level;
    int opponent;

    //note that end time is our variable letting the algo run
    public Move findNextMove(PentagoBoardState board, int player_num){
        int time_limit = 5000;
        long start_time = System.currentTimeMillis();
        opponent = 1 - player_num;

        //initialize the search tree for this iteration of the algorithm
        MC_SearchTree tree = new MC_SearchTree(board); 
        MC_Node rootNode = tree.getRootNode();
        //rootNode.getState().setBoard(board);
        rootNode.getState().setPlayerNum(opponent);
      
        //while the current time - start time is less than elapsed time
        //while(System.currentTimeMillis() - start_time < time_limit){

        //DEBUG change time for iterations when debugging, swith after
        
        int i = 5;
        while(i > 0){
        i--;    
        
        //System.out.print("While loop iteration: " + i);

        

            //SELECTION
            MC_Node promisingNode = selectPromisingNode(rootNode);

            //DEBUG
            /*
            if(i==4){
                System.out.println("Selected initial proising node root");
            }
            else{
                System.out.println("Selected a promising node that was not the root");
            }
            */
            
            //EXPANSION

            //check if the selected node is a terminal node (ends the game)
            //IF THERE IS NO WINNER in this state, expand node?            
            if (promisingNode.getState().getBoard().getWinner() 
              == Board.NOBODY) {
                //System.out.println("Found there was no winner yet: ");
                expandNode(promisingNode);
                //System.out.println("Expanded the node!: ");
            }
            

            //why would the expanded node have hildren? in cases where we generated all the childrem
            //REV note that if we're optimizing move selection, shouldn't select child randomly?
            MC_Node nodeToExplore = promisingNode;
            if (promisingNode.getChildren().size() > 0) {
                nodeToExplore = promisingNode.getRandomChildNode();
            }
            
            //SIMULATION          
            int playoutResult = simulateRandomPlayout(nodeToExplore);
            //System.out.println("Simulated play!");

            //BACKPROPOGATION
            backPropogation(nodeToExplore, playoutResult);
            //System.out.println("Backpropogated!");

            System.out.println("At the end of this iteration our explored node looks like:");
            nodeToExplore.print();
        }

        //print the tree
        //tree.print_tree();

        //build the tree now select the 'best' child node from the root
        //which represents the best move from the current state
        MC_Node winningMoveNode = rootNode.getBestChildNode();
        System.out.println("                SELECTED MOVE:");
        winningMoveNode.print();
        //tree.setRoot(winningMoveNode); //why do we care about setting the root node to the winner node if we don't save?? do e??
        Move bestMove = winningMoveNode.getMove();
        return bestMove;
    }

/****************************************************************************************
SELECTION PHASE

Methods helpful for the selection phase of the MCTS Algo 
****************************************************************************************/
    private MC_Node selectPromisingNode(MC_Node rootNode) {
        //System.out.println("MCST: Entered selectPromisingNode");
        MC_Node node = rootNode;
            
        try{  
            
            while (node.getChildren().size() != 0) {
            //while (node.getChildren() != null){
                //System.out.println("MCST: selectPromisingNode: node has children");
                node = UC_Tree.findBestNodeWithUCT(node);
            }
            
        }
        catch(Exception e){
            System.out.println("MCST: selectPromisingNode: Exception:");
            System.out.println(e.toString());
        }


        //System.out.println("MCST: exitting sel promising node ");

        return node;
    }

/****************************************************************************************
EXPANSION PHASE

Methods helpful for the expansion phase of the MCTS Algo 
****************************************************************************************/
private void expandNode(MC_Node node) {
    Random rand = new Random();
    /* To save on processing in the tree policy, we'll just gen all legal moves,
    then randomly select one and process it, creating a new board, then we have to 
    create a new node with that updated board
    REV: if we want to make our tree policy more optimal, here is where we'll choose what are
    'good' moves, that should be prioritized
    WHAT IS THE BENEFIT of creating all children? I don't see it
    */

    //clone the board, get a (random) legal move, update the new board
    PentagoBoardState newBoard = (PentagoBoardState)node.getState().getBoard().clone();
    ArrayList<PentagoMove> allPossibleMoves = newBoard.getAllLegalMoves();
    PentagoMove move = allPossibleMoves.get(rand.nextInt(allPossibleMoves.size()));
    //Move move = newBoard.getRandomMove(); WTF is up w types here bw move and pentago move
    newBoard.processMove(move);

    //DEBUG
    /*
    System.out.println("MCST: expandNode: randomly selected next move");
    System.out.println("MCST: expandNode: The move was:");
    System.out.println(move.toPrettyString());

    System.out.println("MCST: expandNode: Board now looks like:");
    newBoard.printBoard();
    
    System.out.println("Current node's children");
    System.out.println(node.getChildren());
    */
    
        //now create a node for the board w the new move, update old node as parent
        //need to save the move so we can access it later
        MC_Node newNode = new MC_Node(newBoard);
    try{
        //newNode.getState().setBoard(newBoard);
        newNode.setMove(move);
        newNode.setParent(node);        
        newNode.getState().setPlayerNum(node.getState().getOpponent());
        node.addChild(newNode); 
    }
    catch(Exception e){
        System.out.println("MCST: expandNode: Exception:");
        System.out.println(e.toString());
    }
    //System.out.println("MCST: expandNode: added next move as new node");
}

/****************************************************************************************
SIMULATION PHASE

Methods helpful for the simulation phase of the MCTS Algo 
-REV: if we wanted to optimize, we could also make our rollouts non rand
ie we could actually select moves stategically instead of randomly during rollouts
****************************************************************************************/
private int simulateRandomPlayout(MC_Node node) {
    Random rand = new Random();

    //create a clone of the board and check its win status
    PentagoBoardState tempBoard = (PentagoBoardState)node.getState().getBoard().clone();
    int boardWinnerStatus = tempBoard.getWinner();
    //int turnPlayer = tempBoard.getTurnPlayer(); //when while exits turn player should be winner? NECESSARY?
    
    /*REV we're going to add a simple heavier rollout that will improve on random:
    -offenseive: 
    --count the num of our pieces on diag, horiz and vert spaces*/

    //play the game while no one has won
    while (boardWinnerStatus==Board.NOBODY){
        //turnPlayer = tempBoard.getTurnPlayer();
        
        //turn player gets a random move and processes it
        ArrayList<PentagoMove> allPossibleMoves = tempBoard.getAllLegalMoves();
        PentagoMove move = allPossibleMoves.get(rand.nextInt(allPossibleMoves.size()));
        tempBoard.processMove(move);
        
        boardWinnerStatus = tempBoard.getWinner();
    }

    return boardWinnerStatus;
}


/****************************************************************************************
BACKPROPOGATION PHASE

Methods helpful for the backpropogation phase of the MCTS Algo 
****************************************************************************************/

//TOOD must be prop'd differently depending on the player
private void backPropogation(MC_Node nodeToExplore, int playerNo) {
    MC_Node tempNode = nodeToExplore;
    while (tempNode != null) {
        tempNode.getState().incrementVisit();
        if (tempNode.getState().getPlayerNum() == playerNo) {
            tempNode.getState().addWinScore(win_score);
        }
        tempNode = tempNode.getParent();
    }
}

}