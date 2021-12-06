package timetraking.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import timetracking.firtsmilestone.core.Project;
import timetracking.firtsmilestone.core.Task;
import timetracking.firtsmilestone.core.TimeInterval;
import timetracking.firtsmilestone.core.Timer;

public class TimerTest {
  private static Timer timer;
  private static Task task;

  @BeforeAll
  public static void setup() throws Exception {
    timer = Timer.getInstance();
    Project project = new Project("root", null);
    task = new Task("task", project);
  }

  @AfterAll
  public  static void tearDown() throws Exception {
    timer.stopCounting();
  }

  @Test
  public void timerTaskStartTimeTest() throws Exception {
    Assertions.assertEquals(0, Timer.getInstance().countObservers());
    TimeInterval timerInterval = new TimeInterval(task);

    timerInterval.startTime();
    Assertions.assertEquals(1, Timer.getInstance().countObservers());
    long duration = 4;
    Thread.sleep(duration * Timer.getInstance().getTimerMillisecondsPeriod());

    long timeIntervalDuration = timerInterval.getCurrentDuration();
    timerInterval.stopTime();
  }

  @Test
  public void timerTaskStopTimeTest() throws Exception {
    Assertions.assertEquals(0, Timer.getInstance().countObservers());
    TimeInterval timerInterval = new TimeInterval(task);
    timerInterval.startTime();
    Assertions.assertEquals(1, Timer.getInstance().countObservers());

    long sleepTime = Timer.getInstance().getTimerMillisecondsPeriod();
    Thread.sleep(sleepTime);
    timerInterval.stopTime();

    Assertions.assertEquals(0, Timer.getInstance().countObservers());
    Assertions.assertNotNull(timerInterval.getEndTime());
    Assertions.assertNotSame(timerInterval.getStartTime(), timerInterval.getEndTime());
  }
}
