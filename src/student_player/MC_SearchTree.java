package student_player;

/* Monte Carlo Search Tree representation */

public class MC_SearchTree {
    private MC_Node root;
    private MC_Node curNode; //necessary?

    //constructor
    MC_SearchTree(){
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
}