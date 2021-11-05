package timeTraking.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import timeTracking.core.Project;
import timeTracking.core.Task;
import timeTracking.core.TimeInterval;
import timeTracking.core.Timer;

public class TimerTest {
  private static Timer timer;
  private static Task task;
  private static Project project;

  @BeforeAll
  public static void setup() throws Exception {
    timer = Timer.getInstance();
    project = new Project("root",null);
    task = new Task("task",project);
  }

  @AfterAll
  public  static void tearDown() throws Exception {
    timer.stopCounting();
  }

  @Test
  public void timerTaskStartTimeTest() throws Exception {
      Assertions.assertTrue(Timer.getInstance().countObservers() == 0);
      TimeInterval timerInterval = new TimeInterval(task);

      timerInterval.startTime();
      Assertions.assertTrue(Timer.getInstance().countObservers() == 1);
      long duration = 4;
      Thread.sleep(duration * Timer.getInstance().getTimerMillisecondsPeriod());

      long timeIntervalDuration = timerInterval.getCurrentDuration();
      timerInterval.stopTime(); // In case there is any other test that needs Empty
      Assertions.assertTrue(duration * (Timer.getInstance().getTimerMillisecondsPeriod()) / 1000 == timeIntervalDuration);
  }

  @Test
  public void timerTaskStopTimeTest() throws Exception {
    Assertions.assertTrue(Timer.getInstance().countObservers() == 0);
    TimeInterval timerInterval = new TimeInterval(task);
    timerInterval.startTime();
    Assertions.assertTrue(Timer.getInstance().countObservers() == 1);

    long sleepTime = Timer.getInstance().getTimerMillisecondsPeriod();
    Thread.sleep(sleepTime);
    timerInterval.stopTime();

    Assertions.assertTrue(Timer.getInstance().countObservers()== 0);
    Assertions.assertNotNull(timerInterval.getEndTime());
    Assertions.assertTrue(timerInterval.getStartTime() != timerInterval.getEndTime());
  }
}
