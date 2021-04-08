package student_player;

import pentago_twist.PentagoBoard;
import pentago_twist.PentagoBoardState;

import java.util.Queue;
import java.util.LinkedList;
import java.util.ArrayList;


/* Monte Carlo Search Tree representation 

*/

public class MC_SearchTree {
    private MC_Node root;
    private MC_Node curNode; //necessary?

    //constructor
    MC_SearchTree(PentagoBoardState boardState){
        MC_Node root = new MC_Node(boardState);
        this.root = root;
    }
    
    //setters
    public void setRoot(MC_Node root){
        this.root = root;
    }
    public void setCurrent(MC_Node cur_Node){
        this.curNode = cur_Node;
    }
    //getters
    public MC_Node getRootNode(){
        return this.root;
    }
    public MC_Node getCur_Node(){
        return this.curNode;
    }

    //misc
    public void print_tree(){
        MC_Node curNode = this.root;
        Queue<MC_Node> q = new LinkedList<>();
        ArrayList<MC_Node> children = new ArrayList<MC_Node>();

        q.add(curNode);
        while(q.size() > 0){
            //remove head of queue
            curNode = q.remove();

            //print the move that got us to the board
            System.out.println("NODE \nMove that got us to this board:");
            System.out.println(curNode.getMove().toPrettyString());
            System.out.println("Board now:");
            //print the board
            curNode.getState().getBoard().printBoard();
            System.out.println("Visit count:" + curNode.getState().getVisitCount());
            System.out.println("Win count:" + curNode.getState().getWinScore());
             
            //push the node's children into the queue
            children = curNode.getChildren();
            
            for(MC_Node node : children){
                q.add(node);
            }
        }
    }
}