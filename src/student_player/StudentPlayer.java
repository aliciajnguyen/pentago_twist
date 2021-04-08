package student_player;

import boardgame.Move;
import jdk.nashorn.api.tree.DebuggerTree;
import pentago_twist.PentagoPlayer;
import pentago_twist.PentagoBoardState;
import jdk.nashorn.api.tree.DebuggerTree;

/** A player file submitted by a student. */
public class StudentPlayer extends PentagoPlayer {

    /**
     * You must modify this constructor to return your student number. This is
     * important, because this is what the code that runs the competition uses to
     * associate you with your agent. The constructor should do nothing else.
     */
    public StudentPlayer() {
        super("260424285"); // does this mean I'm return my student number? What does super do?
    }//super with arg? means I'm callingthe super class?

    /**
     * This is the primary method that you need to implement. The ``boardState``
     * object contains the current state of the game, which your agent must use to
     * make decisions.
     */
    public Move chooseMove(PentagoBoardState boardState) {
        Move myMove;

        // You probably will make separate functions in MyTools.
        // For example, maybe you'll need to load some pre-processed best opening
        // strategies...
        //MyTools.getSomething();


        // Is random the best you can do?
        //myMove = boardState.getRandomMove();

        
        //////////////////////////////////////////////////////////////////////START
        /* - starting with basic MCTS implementation -but maybe implement a better strategy for game beginning later
        //also implement EMERGENCY DEFENSE if other player has 4 tokens in a row?
        */

        
    
        
        //clone the board and get necc info
        PentagoBoardState boardClone = (PentagoBoardState)boardState.clone();
        int turnPlayer = boardClone.getTurnPlayer();

        //DEBUG
        int turnNo = boardClone.getTurnNumber();

        //System.out.println("Your Turn:" + turnNo);

        MonteCarloTreeSearch mcts = new MonteCarloTreeSearch();
        myMove = mcts.findNextMove(boardClone, turnPlayer);

        //if its the first move, start with a random move
        //does move start at 0 or 1?
        /*
        if (boardClone.getTurnNumber() == 0){
            myMove = boardClone.getRandomMove();
            //System.out.println("First turn, made rand move ");
            //PUT NON RANDOM STARTING MOVE HERE?
        }
        else{
            //search!
            MonteCarloTreeSearch mcts = new MonteCarloTreeSearch();
            myMove = mcts.findNextMove(boardClone, turnPlayer);
        }
        */

        //DEBUG
        /*

        try{
            if (boardClone.getTurnNumber() == 1){
                System.out.println("Halt for debug at 2nd move");
                Thread.sleep(30000);
            }
        }
        catch(Exception e){
            Thread.currentThread().interrupt();
        }
        */

        /////////////////////////////////////////////////////////////////////////
        

        // Return your move to be processed by the server.
        return myMove;
    }
}