package timetraking.test;

import org.junit.jupiter.api.Test;
import timetracking.firtsmilestone.api.Visitor;
import timetracking.firtsmilestone.core.Project;
import timetracking.firtsmilestone.core.Task;
import timetracking.firtsmilestone.core.Timer;
import timetracking.firtsmilestone.impl.JsonParser;
import timetracking.firtsmilestone.impl.TreePrinter;

public class FirstMilestoneTest {
  private final long TIMER_CLOCK = Timer.getInstance().getTimerMillisecondsPeriod();

  @Test
  public void appendixB() throws Exception {

    // make a small tree of projects and tasks

    Project rootProject = new Project("root", null);
    Project softwareDesign = new Project("software design", rootProject, "java", "flutter");
    Project softwareTesting = new Project("software testing", rootProject, "c++", "Java", "python");
    Project dataBase = new Project("dataBase", rootProject, "SQL", "python", "C++");
    Task transportation = new Task("transportation", rootProject);

    Project problems = new Project("problems", softwareDesign);
    Project projectTimeTracke = new Project("time tracker", softwareDesign);

    final Task firtslist = new Task("firts list", problems, "java");
    final Task secondList = new Task("Second list", problems, "Dart");
    final Task readHandle = new Task("read handle", projectTimeTracke, "Java", "IntelliJ");

    Task firstMilestone = new Task("firstMilestone", projectTimeTracke);

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
    Project dataBase = new Project("dataBasse", rootProject);
    Project transPortation = new Project("task transportation", rootProject);

    Project problems = new Project("problems", software);
    Project projectTimeTracke = new Project("time tracker", software);

    Task firtslist = new Task("firts list", problems);
    Task secondList = new Task("Second list", problems);
    Task readHandle = new Task("read handle", projectTimeTracke);

    Task firstMilestone = new Task("firstMilestone", projectTimeTracke);

    Visitor printer = new TreePrinter();
    printer.visitProject(rootProject);
  }
}