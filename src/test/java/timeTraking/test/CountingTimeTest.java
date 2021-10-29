package timeTraking.test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timeTracking.api.MenuInterface;
import timeTracking.api.Visitor;
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

        Project rootProject = new Project("root", null);
        Project softwareDessign = new Project("software dessgin", rootProject);
        Project softwareTesting = new Project("software testing", rootProject);
        Project dataBase = new Project("dataBasse",rootProject);
        Task transportation = new Task("transportation", rootProject);

        Project problems = new Project("problems",softwareDessign);
        Project projectTimeTracke = new Project("time tracker", softwareDessign);

        Task firtslist = new Task("firts list", problems);
        Task secondList = new Task("Second list", problems);
        Task readHandle = new Task("read handle",projectTimeTracke);

        Task firstMilestone = new Task("firstMilestone",projectTimeTracke);

        transportation.startNewInterval();
        Thread.sleep(4000);
        transportation.stopActualInterval();

        Thread.sleep(2000);

        firtslist.startNewInterval();
        Thread.sleep(6000);

        secondList.startNewInterval();
        Thread.sleep(4000);
        firtslist.stopActualInterval();

        Thread.sleep(2000);

        secondList.stopActualInterval();
        Thread.sleep(2000);

        transportation.startNewInterval();
        Thread.sleep(4000);
        transportation.stopActualInterval();

        System.out.println("Project total time: " + rootProject.getTotalTime());
    }

    @Test
    public void appendixA() throws Exception {
        Project rootProject = new Project("root", null);
        Project software = new Project("software dessgin", rootProject);
        Project softwareTesting = new Project("software testing", rootProject);
        Project dataBase = new Project("dataBasse",rootProject);
        Project transPortation = new Project("task transportation", rootProject);

        Project problems = new Project("problems",software);
        Project projectTimeTracke = new Project("time tracker", software);

        Task firtslist = new Task("firts list", problems);
        Task secondList = new Task("Second list", problems);
        Task readHandle = new Task("read handle",projectTimeTracke);

        Task firstMilestone = new Task("firstMilestone",projectTimeTracke);

        Visitor printer = new TreePrinter();
        printer.visitProject(rootProject);
    }
}