package timeTraking.test.time;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import timeTracking.core.Timer;

public class TimerTest {
  private static Timer timer;

  @BeforeAll
  public static void setup() throws Exception {
    timer = Timer.getInstance();
  }

  @Test
  public void timerTaskStartCountingTest() throws Exception {
      timer.startCounting();

      long startTime = timer.getPassedTime();
      Thread.sleep(3000);
      long endTime = timer.getPassedTime();
      Assertions.assertTrue(startTime < endTime);
  }

  @Test
  public void timerTaskStopCountingTest() throws Exception {
    timer.startCounting();
    Thread.sleep(1000);
    long startTime = timer.getPassedTime();
    timer.stopCounting();
    Thread.sleep(1000);
    long endTime = timer.getPassedTime();
    Assertions.assertTrue(startTime == endTime);
  }
}
