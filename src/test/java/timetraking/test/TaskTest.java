package timetraking.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timetracking.firtsmilestone.core.Project;
import timetracking.firtsmilestone.core.Task;
import timetracking.firtsmilestone.core.TimeInterval;
import timetracking.firtsmilestone.core.Timer;

public class TaskTest {
  private Task task;
  private final long timerClock = Timer.getInstance().getTimerMillisecondsPeriod();

  @BeforeEach
  public void setup() throws Exception {
    Project emptyProject = new Project("demo", null);
    task = new Task("demo", emptyProject);
  }

  @Test
  public void startStopOnlyOneIntervalTaskTest() throws Exception {
    Assertions.assertEquals(0, task.getTimeIntervalList().size());
    Assertions.assertNull(task.getTimeInterval());
    task.startNewInterval();

    Assertions.assertEquals(1, task.getTimeIntervalList().size());
    Assertions.assertNotNull(task.getTimeInterval());
    Assertions.assertNotNull(task.getTimeInterval().getStartTime());
    Assertions.assertNull(task.getTimeInterval().getEndTime());
    long initialDuration = 4;

    Thread.sleep(initialDuration * timerClock);

    task.stopActualInterval();
    Assertions.assertEquals(1, task.getTimeIntervalList().size());
    Assertions.assertNull(task.getTimeInterval());
    Assertions.assertEquals(task.getTotalTime(), initialDuration * (timerClock / 1000));
  }

  @Test
  public void startStopMultipleIntervalTaskTest() throws Exception {
    Assertions.assertEquals(0, task.getTimeIntervalList().size());
    Assertions.assertNull(task.getTimeInterval());
    int timesToCreate = 4;

    createTasks(timesToCreate);
    Assertions.assertEquals(task.getTimeIntervalList().size(), timesToCreate);
    Assertions.assertNull(task.getTimeInterval());

    long totalDuration = task.getTotalTime();
    Assertions.assertEquals(totalDuration, timesToCreate * (timerClock) / 1000);
  }

  @Test
  public void getTotalTimeMultipleTimesTest() throws Exception {
    Assertions.assertEquals(0, task.getTimeIntervalList().size());
    Assertions.assertNull(task.getTimeInterval());
    int timesToCreate = 3;
    createTasks(timesToCreate);
    long currentTimeDuration = task.getTotalTime();
    Assertions.assertTrue(currentTimeDuration > 0);
    Thread.sleep(timerClock);

    Assertions.assertTrue(currentTimeDuration >= timesToCreate);
    Assertions.assertEquals(currentTimeDuration, task.getTotalTime());
  }

  @Test
  public void startTimeStopTimeWithDifferentTimeIntervalsTest() throws Exception {
    Assertions.assertEquals(0, task.getTimeIntervalList().size());
    Assertions.assertNull(task.getTimeInterval());
    TimeInterval firstTimeInterval = task.startNewInterval();
    Assertions.assertNotNull(firstTimeInterval);

    Assertions.assertNotNull(firstTimeInterval.getStartTime());
    Assertions.assertNull(firstTimeInterval.getEndTime());
    Thread.sleep(timerClock);

    firstTimeInterval = task.getTimeInterval();
    Thread.sleep(timerClock);

    TimeInterval stoppedFirstTimeInterval = task.stopActualInterval();

    Assertions.assertNotNull(stoppedFirstTimeInterval);
    Assertions.assertEquals(stoppedFirstTimeInterval.getCurrentDuration(), firstTimeInterval.getCurrentDuration());

    Thread.sleep(timerClock * 2);

    Assertions.assertEquals(stoppedFirstTimeInterval.getCurrentDuration(), firstTimeInterval.getCurrentDuration());
  }

  private void createTasks(int timesToCreate) throws Exception {
    for (int i = 0; i < timesToCreate; i++) {
      task.startNewInterval();
      Thread.sleep(timerClock);
      task.stopActualInterval();
      Thread.sleep(timerClock);
    }
  }
}