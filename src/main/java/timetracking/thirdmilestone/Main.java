package timetracking.thirdmilestone;

import timetracking.firtsmilestone.impl.DemoTree;
import timetracking.thirdmilestone.WebServer;

/*
 * This class is the one wich starts our project.
*/
public class Main {
  public static void main(String[] args) throws Exception {
    DemoTree demoTree = new DemoTree();
    new WebServer(demoTree.getRootProject());
  }
}
