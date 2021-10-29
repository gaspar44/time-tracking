package timeTraking.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timeTracking.core.Project;
import timeTracking.core.Task;
import timeTracking.core.TimeInterval;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TaskTest {
  private Task task;
  private Project emptyProject;

  @BeforeEach
  public void setup() throws Exception {
    emptyProject = new Project("demo",null);
    task = new Task("demo",emptyProject);
  }

  @Test
  public void startStopOnlyOneIntervalTaskTest() throws Exception {
    Assertions.assertTrue(task.getTimeIntervalList().size() == 0);
    Assertions.assertNull(task.getTimeInterval());
    task.startNewInterval();

    Assertions.assertTrue(task.getTimeIntervalList().size() == 1);
    Assertions.assertNotNull(task.getTimeInterval());
    Assertions.assertNotNull(task.getTimeInterval().getStartTime());
    Assertions.assertNull(task.getTimeInterval().getEndTime());
    long initialDuration = 4;

    Thread.sleep(initialDuration * 1000);

    task.stopActualInterval();
    Assertions.assertTrue(task.getTimeIntervalList().size() == 1);
    Assertions.assertNull(task.getTimeInterval());
    Assertions.assertTrue(task.getTotalTime() >= initialDuration);
  }

  @Test
  public void startStopMultipleIntervalTaskTest() throws Exception {
    Assertions.assertTrue(task.getTimeIntervalList().size() == 0);
    Assertions.assertNull(task.getTimeInterval());
    int timesToCreate = 4;

    createTasks(timesToCreate);
    Assertions.assertTrue(task.getTimeIntervalList().size() == timesToCreate);
    Assertions.assertNull(task.getTimeInterval());

    long totalDuration = task.getTotalTime();
    Assertions.assertTrue(totalDuration >= timesToCreate);
  }

  @Test
  public void createProjectTest() throws Exception {

    // Creation of clock with period of 2 s
    int time = 2000;
    int totalTime;
    Thread interval = new Thread();

    // make a small tree of projects and tasks
    Project root = new Project("root", null);
    Project p1 = new Project("P1", root);
    Project p2 = new Project("P2", root);
    Task t1 = new Task("T1", root);
    Task t2 = new Task("T2", p1);
    Task t3 = new Task("T3", p2);


    // TASK 1
    interval.start();
    interval.sleep(1000);

    t1.startNewInterval();
    interval.sleep(1000);
    t1.stopActualInterval();

    System.out.println("Name "+"   Initial date " + "          Final date " + "                   Duration ");
    System.out.println(" " + t1.getName() + "   " + t1.getStartedTime() + "    " + t1.getEndedTime() + "    " + t1.getHumanReadableTimeDuration());


    for (int i = 0; i < 2; i++)
    {
      t1.startNewInterval();
      interval.sleep(2000);
      t1.stopActualInterval();

      System.out.println("Name "+"   Initial date " + "          Final date " + "                   Duration ");
      System.out.println(" " + t1.getName() + "   " + t1.getStartedTime() + "    " + t1.getEndedTime() + "    " + t1.getHumanReadableTimeDuration());
    }

    System.out.println("Name "+"   Initial date " + "          Final date " + "                   Duration ");
    System.out.println(" " + root.getName() + "   " + t1.getStartedTime() + "    " + t1.getEndedTime() + "    " + root.getHumanReadableTimeDuration());
    interval.stop();

    // TASK 2

    interval.sleep(1000);
    t2.startNewInterval();
    interval.sleep(6000);
    t2.stopActualInterval();

    System.out.println(" Name "+"   Initial date " + "          Final date " + "                  Duration ");
    System.out.println(" " + t2.getName() + "   " + t2.getStartedTime() + "    " + t2.getEndedTime() + "    " + t2.getHumanReadableTimeDuration());


    for (int i = 0; i < 2; i++)
    {
      t2.startNewInterval();
      interval.sleep(2000);
      t2.stopActualInterval();

      System.out.println(" Name "+"   Initial date " + "          Final date " + "                  Duration ");
      System.out.println(" " + t2.getName() + "   " + t2.getStartedTime() + "    " + t2.getEndedTime() + "    " + t2.getHumanReadableTimeDuration());
    }
    System.out.println("Name "+ "          Total time " + "                   Duration ");
    System.out.println(" " + root.getName() + "   " + root.getTotalTime()+ "    " + root.getHumanReadableTimeDuration());

    // TASK 3

    interval.sleep(1000);
    t3.startNewInterval();
    interval.sleep(2000);
    t3.stopActualInterval();

    System.out.println(" Name "+"   Initial date " + "          Final date " + "                  Duration ");
    System.out.println(" " + t3.getName() + "   " + t3.getStartedTime() + "    " + t3.getEndedTime() + "    " + t3.getHumanReadableTimeDuration());

    for (int i = 0; i < 2; i++)
    {
      t3.startNewInterval();
      interval.sleep(4000);
      t3.stopActualInterval();

      System.out.println(" Name "+"   Initial date " + "          Final date " + "                  Duration ");
      System.out.println(" " + t3.getName() + "   " + t3.getStartedTime() + "    " + t3.getEndedTime() + "    " + t3.getHumanReadableTimeDuration());
    }
    System.out.println("Name "+ "          Total time " + "                   Duration ");
    System.out.println(" " + root.getName() + "   " + root.getTotalTime()+ "    " + root.getHumanReadableTimeDuration());

  }


  @Test
  public void getTotalHumanTimeTest() throws Exception {
    createTasks(5);
    task.getHumanReadableTimeDuration();
  }

  @Test
  public void getTotalTimeMultipleTimesTest() throws Exception {
    Assertions.assertTrue(task.getTimeIntervalList().size() == 0);
    Assertions.assertNull(task.getTimeInterval());
    int timesToCreate = 3;
    createTasks(timesToCreate);
    long currentTimeDuration = task.getTotalTime();
    Assertions.assertTrue(currentTimeDuration > 0);
    Thread.sleep(3000);

    Assertions.assertTrue(currentTimeDuration >= timesToCreate);
    Assertions.assertTrue(currentTimeDuration == task.getTotalTime());
  }



  @Test
  public void startTimeStopTimeWithDifferentTimeIntervalsTest() throws Exception {
    Assertions.assertTrue(task.getTimeIntervalList().size() == 0);
    Assertions.assertNull(task.getTimeInterval());
    TimeInterval firstTimeInterval = task.startNewInterval();
    Assertions.assertNotNull(firstTimeInterval);

    Assertions.assertNotNull(firstTimeInterval.getStartTime());
    Assertions.assertNull(firstTimeInterval.getEndTime());
    Thread.sleep(1000);

    firstTimeInterval = task.getTimeInterval();
    Thread.sleep(1000);

    TimeInterval stoppedFirstTimeInterval = task.stopActualInterval();

    Assertions.assertNotNull(stoppedFirstTimeInterval);
    Assertions.assertTrue(stoppedFirstTimeInterval.getCurrentDuration() == firstTimeInterval.getCurrentDuration());

    Thread.sleep(2000);

    Assertions.assertTrue(stoppedFirstTimeInterval.getCurrentDuration() == firstTimeInterval.getCurrentDuration());
  }

  private void createTasks(int timesToCreate) throws Exception {
    for (int i = 0; i < timesToCreate; i++) {
      task.startNewInterval();
      Thread.sleep(1000);
      task.stopActualInterval();
      Thread.sleep(1000);
    }
  }
}