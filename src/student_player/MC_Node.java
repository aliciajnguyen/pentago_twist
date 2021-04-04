package student_player;

import boardgame.Move;

//import pentago_twist.PentagoBoardState;
//import pentago_twist.PentagoPlayer;
//import pentago_twist.PentagoMove;

import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;
import java.util.Comparator;

/* Node implementation for MCST
*
* Uses MC_NodeState
*/
public class MC_Node {

    //private PentagoBoardState state;
    //private Piece[][] board_rep;
    private MC_NodeState state;
    private MC_Node parent;
    private ArrayList<MC_Node> children;
    private Move move; //the move that got us to this board state from the parent

    //constructor, needs to create a state
    MC_Node(){
        MC_NodeState state = new MC_NodeState();
        this.state = state;
    }

    //setters
    public void setState(MC_NodeState state){
        this.state = state;
    }
    public void setParent(MC_Node parent){
        this.parent = parent;
    }
    public void setMove(Move move){
        this.move = move;
    }


    //getters
    public MC_NodeState getState(){
        return this.state;
    }
    public MC_Node getParent(){
        return this.parent;
    }
    public ArrayList<MC_Node> getChildren(){
        return this.children;
    }
    public Move getMove(){
        return this.move;
    }


    //misc methodss
    
    //method to get a random child from this node's child list
    public MC_Node getRandomChildNode(){
        Random rand = new Random();

        ArrayList<MC_Node> children = this.getChildren();
        return children.get(rand.nextInt(children.size()));
    }

    //a method that takes this node, and returns the child node with the max score
    //REV assuming the 'best move' is the one with the highest denominator??
    public MC_Node getBestChildNode(){
        MC_Node bestChild = Collections.max(this.getChildren(), 
            Comparator.comparing(c -> c.getState().getVisitCount()));        
        return bestChild;

    }

}