package timeTraking.test.time;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import timeTracking.core.TimeInterval;
import timeTracking.core.Timer;

import java.util.Observer;

public class TimerTest {
  private static Timer timer;

  @BeforeAll
  public static void setup() throws Exception {
    timer = Timer.getInstance();
  }

  @Test
  public void timerTaskStartCountingTest() throws Exception {
      Assertions.assertTrue(Timer.getInstance().getObservers().size() == 0);
      TimeInterval timerInterval = new TimeInterval();
      timerInterval.startCounting();
      Assertions.assertTrue(Timer.getInstance().getObservers().size() == 1);
      int duration = 4;
      timerInterval.startCounting();
      Thread.sleep(3000);
      Assertions.assertTrue(duration <= timerInterval.getDuration());
  }

/*  @Test
  public void timerTaskStopCountingTest() throws Exception {
    timer.startCounting();
    Thread.sleep(1000);
    long startTime = timer.getPassedTime();
    timer.stopCounting();
    Thread.sleep(1000);
    long endTime = timer.getPassedTime();
    Assertions.assertTrue(startTime == endTime);
  }*/
}
