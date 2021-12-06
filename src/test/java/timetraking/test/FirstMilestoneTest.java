package timetraking.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import timetracking.firtsmilestone.api.Visitor;
import timetracking.firtsmilestone.core.Project;
import timetracking.firtsmilestone.core.Task;
import timetracking.firtsmilestone.core.Timer;
import timetracking.firtsmilestone.impl.DemoTree;
import timetracking.firtsmilestone.impl.JsonParser;
import timetracking.firtsmilestone.impl.TreePrinter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FirstMilestoneTest {
  private final long timerClock = Timer.getInstance().getTimerMillisecondsPeriod();

  @AfterEach
  public void afterEach() throws IOException {
    File file = new File("demo.json");
    Files.deleteIfExists(file.toPath());
  }


  @Test
  public void appendixB() throws Exception {

    // make a small tree of projects and tasks
    DemoTree demoTree = new DemoTree();
    Task transportation = demoTree.getTransPortation();

    final Task firtslist = demoTree.getFirtslist();
    final Task secondList = demoTree.getSecondList();

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

    Project rootProject = demoTree.getRootProject();
    System.out.println("Project total time: " + rootProject.getTotalTime());
    JsonParser.getInstance().setFileName("demo.json");
    rootProject.acceptVisitor(JsonParser.getInstance());
    Project newRootProject = JsonParser.getInstance().getProjectsFromJson("demo.json");
    System.out.println(newRootProject.getName());
  }

  @Test
  public void appendixA() {
    DemoTree demoTree = new DemoTree();
    Project rootProject = demoTree.getRootProject();


    Visitor printer = new TreePrinter();
    printer.visitProject(rootProject);
  }
}