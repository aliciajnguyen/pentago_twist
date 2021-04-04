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
        if (nodeVisit == 0) {
            return Integer.MAX_VALUE;
        }
        return ((double) nodeWinScore / (double) nodeVisit) 
          + Math.sqrt(2) * Math.sqrt(Math.log(totalVisit) / (double) nodeVisit);
    }

    public static MC_Node findBestNodeWithUCT(MC_Node node) {
        int parentVisit = node.getState().getVisitCount();
        return Collections.max(
          node.getChildren(),
          Comparator.comparing(c -> calcUCTValue(parentVisit, 
            c.getState().getWinScore(), c.getState().getVisitCount())));
    }
    //return max element in given collection of objects
    //use comparator to order objects of user defined classes
    //

    //https://www.geeksforgeeks.org/collections-max-method-in-java-with-examples/
    //https://www.geeksforgeeks.org/comparator-interface-java/
    //https://www.baeldung.com/java-8-comparator-comparing
}