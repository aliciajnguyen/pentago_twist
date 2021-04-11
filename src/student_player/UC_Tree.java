package student_player;

import java.util.Collections;
import java.util.Comparator;

//REV: Need to check that it was alright for me to use this source code

/*The upper confidence tree calculations for our MCTree Seach Tree
*
* External Code Sources (also in Sources Cited):
* https://www.baeldung.com/java-monte-carlo-tree-search
*/
public class UC_Tree {
    public static double calcUCTValue(int totalVisit, double nodeWinScore, int nodeVisit) {
      //System.out.println("UC_Tree: In calcUCTValue ");
      if (nodeVisit == 0) {
        return Integer.MAX_VALUE;
      }
      double UCTValue = ((double) nodeWinScore / (double) nodeVisit) 
        + 1.41 * Math.sqrt(Math.log(totalVisit) / (double) nodeVisit);
      
      //DEBUG
      //TODO why isn't this printing?
      //System.out.println("       Calculated UCT score: " + UCTValue);
      //System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!");

      return UCTValue;
    }

    public static MC_Node findBestNodeWithUCT(MC_Node node) {
      //TO DO: must be calculated differently according to player

      //System.out.println("UC_Tree: In find bestNodeWithUCT ");
      int parentVisit = node.getState().getVisitCount();
      
      //TODO just make UCTValye an attribute of node
      //then compute and update for all children in list
      //pick highest //use comparator sort

      for (MC_Node n : node.getChildren()){
        n.setUCTValue(calcUCTValue(parentVisit, n.getState().getWinScore(), n.getState().getVisitCount()));

        //DEBUG
        /*
        System.out.println("///////////////////////////////////////////////////////");
        System.out.println("The node whose UCT value was calculated:");
        System.out.println("///////////////////////////////////////////////////////");
        n.print();
        System.out.println("UCT VALUE: " + n.getUCTValue());
        */
      }

      
      //then sort? CHECK HERE:https://www.baeldung.com/java-8-comparator-comparing
      MC_Node bestNode = Collections.max(node.getChildren(), Comparator.comparing(c -> c.getUCTValue()));
      //System.out.println("Child w best UCT value (to be selected): " + n.getUCTValue());

      //https://stackoverflow.com/questions/5805602/how-to-sort-list-of-objects-by-some-property
      //wtf
      

      /*
      //working? maybe?
      MC_Node bestNode = Collections.max(
      node.getChildren(),
      Comparator.comparing(c -> calcUCTValue(parentVisit, 
            c.getState().getWinScore(), c.getState().getVisitCount())));
      */

      return bestNode;
    }
    //return max element in given collection of objects
    //use comparator to order objects of user defined classes
    //

    //https://www.geeksforgeeks.org/collections-max-method-in-java-with-examples/
    //https://www.geeksforgeeks.org/comparator-interface-java/
    //https://www.baeldung.com/java-8-comparator-comparing
}