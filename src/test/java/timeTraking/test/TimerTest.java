package timeTraking.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import timeTracking.core.TimeInterval;
import timeTracking.core.Timer;

public class TimerTest {
  private static Timer timer;

  @BeforeAll
  public static void setup() throws Exception {
    timer = Timer.getInstance();
  }

  @AfterAll
  public  static void tearDown() throws Exception {
    timer.stopCounting();
  }

  @Test
  public void timerTaskStartTimeTest() throws Exception {
      Assertions.assertTrue(Timer.getInstance().getObservers().size() == 0);
      TimeInterval timerInterval = new TimeInterval();

      timerInterval.startTime();
      Assertions.assertTrue(Timer.getInstance().getObservers().size() == 1);
      long duration = 4;
      Thread.sleep(duration * 1000);

      long timeIntervalDuration = timerInterval.getCurrentDuration();
      timerInterval.stopTime(); // In case there is any other test that needs Empty
      Assertions.assertTrue(duration == timeIntervalDuration);
  }

  @Test
  public void timerTaskStopTimeTest() throws Exception {
    Assertions.assertTrue(Timer.getInstance().getObservers().size() == 0);
    TimeInterval timerInterval = new TimeInterval();
    timerInterval.startTime();
    Assertions.assertTrue(Timer.getInstance().getObservers().size() == 1);

    long sleepTime = 1000;
    Thread.sleep(sleepTime);
    timerInterval.stopTime();

    Assertions.assertTrue(Timer.getInstance().getObservers().size() == 0);
    Assertions.assertNotNull(timerInterval.getEndTime());
    Assertions.assertTrue(timerInterval.getStartTime() != timerInterval.getEndTime());
    Assertions.assertTrue(timerInterval.getStartTime().getTime() == timerInterval.getEndTime().getTime() - sleepTime);
  }
}
