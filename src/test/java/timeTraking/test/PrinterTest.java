package timeTraking.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timetracking.api.MenuInterface;
import timetracking.core.Project;
import timetracking.core.Task;
import timetracking.impl.ConsoleMenu;
import timetracking.impl.TreePrinter;

public class PrinterTest {

    private static MenuInterface menu;
    private String projectName = "root";
    private String taskName = "task";

    @BeforeEach
    public void setup() throws Exception {
        menu = new ConsoleMenu();
    }

    @Test
    public void createProjectTest() throws Exception {

        // make a small tree of projects and tasks
        Project root = new Project("root", null);
        Project p1 = new Project("P1", root);
        Project p2 = new Project("P2", root);
        Task t1 = new Task("T1", root);
        Task t2 = new Task("T2", p1);
        Task t3 = new Task("T3", p2);

        // make the printer
        TreePrinter tree = new TreePrinter();
        root.acceptVisitor(tree);

    }
}