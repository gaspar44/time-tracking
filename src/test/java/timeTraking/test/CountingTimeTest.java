package timeTraking.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timetracking.api.MenuInterface;
import timetracking.api.Visitor;
import timetracking.core.Project;
import timetracking.core.Task;
import timetracking.core.Timer;
import timetracking.impl.ConsoleMenu;
import timetracking.impl.JsonParser;
import timetracking.impl.TreePrinter;

public class CountingTimeTest {
    private static MenuInterface menu;
    private String projectName = "root";
    private String taskName = "task";
    private final long TIMER_CLOCK = Timer.getInstance().getTimerMillisecondsPeriod();

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
        Project dataBase = new Project("dataBase",rootProject);
        Task transportation = new Task("transportation", rootProject);

        Project problems = new Project("problems",softwareDessign);
        Project projectTimeTracke = new Project("time tracker", softwareDessign);

        Task firtslist = new Task("firts list", problems);
        Task secondList = new Task("Second list", problems);
        Task readHandle = new Task("read handle",projectTimeTracke);

        Task firstMilestone = new Task("firstMilestone",projectTimeTracke);

        transportation.startNewInterval();
        Thread.sleep(TIMER_CLOCK * 2);
        transportation.stopActualInterval();

        Thread.sleep(TIMER_CLOCK);

        firtslist.startNewInterval();
        Thread.sleep(TIMER_CLOCK * 3);

        secondList.startNewInterval();
        Thread.sleep(TIMER_CLOCK * 2);
        firtslist.stopActualInterval();

        Thread.sleep(TIMER_CLOCK);

        secondList.stopActualInterval();
        Thread.sleep(TIMER_CLOCK);

        transportation.startNewInterval();
        Thread.sleep(TIMER_CLOCK * 3);
        transportation.stopActualInterval();

        System.out.println("Project total time: " + rootProject.getTotalTime());
        JsonParser.getInstance().setFileName("demo.json");
        rootProject.acceptVisitor(JsonParser.getInstance());
        Project newRootProject = JsonParser.getInstance().getProjectsFromJson("demo.json");
        System.out.println(newRootProject.getName());

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