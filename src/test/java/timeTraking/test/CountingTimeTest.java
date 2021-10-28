package timeTraking.test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timeTracking.api.MenuInterface;
import timeTracking.core.Project;
import timeTracking.core.Task;
import timeTracking.impl.ConsoleMenu;
import timeTracking.impl.TreePrinter;

public class CountingTimeTest {


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

        t1.startNewInterval();
        Thread.sleep(2000);
        t1.stopActualInterval();

        System.out.println("Name "+"   Initial date " + "          Final date " + "                   Duration ");
        System.out.println(" " + t1.getName() + "   " + t1.getStartedTime() + "    " + t1.getEndedTime() + "    " + t1.getHumanReadableTimeDuration());


        Thread.sleep(2000);

        t2.startNewInterval();
        Thread.sleep(5000);
        t2.stopActualInterval();

        System.out.println(" Name "+"   Initial date " + "          Final date " + "                  Duration ");
        System.out.println(" " + t2.getName() + "   " + t2.getStartedTime() + "    " + t2.getEndedTime() + "    " + t2.getHumanReadableTimeDuration());

    }
}