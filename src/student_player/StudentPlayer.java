package student_player;

import boardgame.Move;
import jdk.nashorn.api.tree.DebuggerTree;
//import jdk.tools.jlink.internal.Platform;
import pentago_twist.PentagoPlayer;
import pentago_twist.PentagoBoardState;
//import pentago_twist.PentagoMove;
import jdk.nashorn.api.tree.DebuggerTree;
//import static pentago_twist.PentagoBoardState.Piece.*;

import java.util.ArrayList;
import java.util.Random;

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
        Move myMove;
        int turnNum = boardClone.getTurnNumber();
        int mode = 0; //DEBUG REV TODO 0 for learning mode, 1 for read mode

        //Random rand = new Random(System.currentTimeMillis());
       
        //First player to play IS ALWAYS WHITE, but player 0 or 1 may be player who plays first

        //if its the first or second move we use a preplanned strategy
        if (turnNum == 0 || turnNum == 1){
            myMove = MyTools.getInitMove(boardClone, turnPlayer);
            //DEBUG
            //System.out.println("Initial move was: " + myMove.toPrettyString());
            //boardClone.printBoard();
        }
        else{
            MonteCarloTreeSearch mcts = new MonteCarloTreeSearch();
            myMove = mcts.findNextMove(boardClone, turnPlayer);
        }

        //save turn to be written to file
        //DEBUG
        //MyTools.saveTurn(boardClone, myMove);

        // Return your move to be processed by the server.
        return myMove;
    }
}