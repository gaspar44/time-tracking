package timeTraking.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timeTracking.core.Project;
import timeTracking.core.Task;
import timeTracking.core.TimeInterval;

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
