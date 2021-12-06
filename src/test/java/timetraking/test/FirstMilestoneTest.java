package timetraking.test;

import org.junit.jupiter.api.Test;
import timetracking.firtsmilestone.api.Visitor;
import timetracking.firtsmilestone.core.Project;
import timetracking.firtsmilestone.core.Task;
import timetracking.firtsmilestone.core.Timer;
import timetracking.firtsmilestone.impl.DemoTree;
import timetracking.firtsmilestone.impl.JsonParser;
import timetracking.firtsmilestone.impl.TreePrinter;

public class FirstMilestoneTest {
  private final long timerClock = Timer.getInstance().getTimerMillisecondsPeriod();

  @Test
  public void appendixB() throws Exception {

    // make a small tree of projects and tasks
    DemoTree demoTree = new DemoTree();
    Project rootProject = demoTree.getRootProject();
    Project softwareDesign = demoTree.getSoftwareDesign();
    Project softwareTesting = demoTree.getSoftwareTesting();
    Project dataBase = demoTree.getDataBase();
    Task transportation = demoTree.getTransPortation();

    Project problems = demoTree.getProblems();
    Project projectTimeTracke = demoTree.getProjectTimeTracke();

    final Task firtslist = demoTree.getFirtslist();
    final Task secondList = demoTree.getSecondList();
    final Task readHandle = demoTree.getReadHandle();

    Task firstMilestone = demoTree.getFirstMilestone();

    transportation.startNewInterval();
    Thread.sleep(timerClock * 2);
    transportation.stopActualInterval();

    Thread.sleep(timerClock);

    firtslist.startNewInterval();
    Thread.sleep(timerClock * 3);

    secondList.startNewInterval();
    Thread.sleep(timerClock * 2);
    firtslist.stopActualInterval();

    Thread.sleep(timerClock);

    secondList.stopActualInterval();
    Thread.sleep(timerClock);

    transportation.startNewInterval();
    Thread.sleep(timerClock * 3);
    transportation.stopActualInterval();

    System.out.println("Project total time: " + rootProject.getTotalTime());
    JsonParser.getInstance().setFileName("demo.json");
    rootProject.acceptVisitor(JsonParser.getInstance());
    Project newRootProject = JsonParser.getInstance().getProjectsFromJson("demo.json");
    System.out.println(newRootProject.getName());
  }

  @Test
  public void appendixA() throws Exception {
    DemoTree demoTree = new DemoTree();
    Project rootProject = demoTree.getRootProject();


    Visitor printer = new TreePrinter();
    printer.visitProject(rootProject);
  }
}