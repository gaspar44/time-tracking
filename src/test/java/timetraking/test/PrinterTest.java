package timetraking.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timetracking.firtsmilestone.core.Project;
import timetracking.firtsmilestone.core.Task;
import timetracking.firtsmilestone.impl.TreePrinter;

public class PrinterTest {

  @Test
  public void createProjectTest() {

    // make a small tree of projects and tasks
    Project root = new Project("root", null);
    Project p1 = new Project("P1", root);
    Project p2 = new Project("P2", root);
    Project p3 = new Project("P3", root, "Java");
    Task t1 = new Task("T1", root);
    Task t2 = new Task("T2", p1);
    Task t3 = new Task("T3", p2);

    // make the printer
    TreePrinter tree = new TreePrinter();
    root.acceptVisitor(tree);

  }
}