package timeTraking.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timeTraking.test.impl.MockTask;

public class TaskTest {
  private MockTask task;

  @BeforeEach
  public void setup() throws Exception {
    task = new MockTask();
  }

  @Test
  public void startStopOnlyOneIntervalTaskTest() throws Exception {
    Assertions.assertTrue(task.getTimeIntervalList().size() == 0);
    Assertions.assertNull(task.getTimeInterval());
    task.startTime();

    Assertions.assertTrue(task.getTimeIntervalList().size() == 1);
    Assertions.assertNotNull(task.getTimeInterval());
    Assertions.assertNotNull(task.getTimeInterval().getStartTime());
    Assertions.assertNull(task.getTimeInterval().getEndTime());
    long initialDuration = 4;

    Thread.sleep(initialDuration * 1000);

    task.stopTime();
    Assertions.assertTrue(task.getTimeIntervalList().size() == 1);
    Assertions.assertNull(task.getTimeInterval());
    Assertions.assertTrue(task.getTotalDuration() >= initialDuration);
  }

  @Test
  public void startStopMultipleIntervalTaskTest() throws Exception {
    Assertions.assertTrue(task.getTimeIntervalList().size() == 0);
    Assertions.assertNull(task.getTimeInterval());
    int timesToCreate = 4;

    createTasks(timesToCreate);
    Assertions.assertTrue(task.getTimeIntervalList().size() == timesToCreate);
    Assertions.assertNull(task.getTimeInterval());

    long totalDuration = task.getTotalDuration();
    Assertions.assertTrue(totalDuration == timesToCreate);
  }

  @Test
  public void getTotalHumanTimeTest() throws Exception {
    createTasks(5);
    System.out.println(task.parseTimeToHumanReading().toString());
  }

  private void createTasks(int timesToCreate) throws Exception {
    for (int i = 0; i < timesToCreate; i++) {
      task.startTime();
      Thread.sleep(1000);
      task.stopTime();
    }
  }
}
