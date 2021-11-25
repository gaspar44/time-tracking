package timetraking.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timetracking.firtsmilestone.core.Project;
import timetracking.firtsmilestone.core.Task;
import timetracking.firtsmilestone.core.Timer;

public class ProjectTest {
  private Project project;
  private final long timerClock = Timer.getInstance().getTimerMillisecondsPeriod();

  @BeforeEach
  public void setup() throws Exception {
    project = new Project("demo", null);
  }

  @Test
  public void addTaskToProjectTest() {
    Assertions.assertEquals(0, project.getComponents().size());
    Assertions.assertEquals(0, project.getTotalTime());

    Task task = new Task("holis", project);
    Assertions.assertEquals(1, project.getComponents().size());
  }

  @Test
  public void startCountingTimeAtProject() throws Exception {
    Task task = new Task("holis", project);
    Assertions.assertTrue(project.getComponents().size() == 1);
    Assertions.assertTrue(project.getTotalTime() == 0);
    task.startNewInterval();
    Thread.sleep(timerClock);

    Assertions.assertTrue(project.getTotalTime() > 0);

    task.stopActualInterval();
    long currentDuration = task.getTotalTime();
    Thread.sleep(timerClock * 2);
    Assertions.assertEquals(currentDuration, project.getTotalTime());
  }

  @Test
  public void startCountingTimeMultipleTaskTest() throws Exception {
    Task task1 = new Task("holis", project);
    final Task task2 = new Task("holis2", project);

    Assertions.assertTrue(project.getComponents().size() == 2);
    Assertions.assertTrue(project.getTotalTime() == 0);

    task1.startNewInterval();
    Thread.sleep(timerClock);

    task1.stopActualInterval();
    long task1CurrenTime = task1.getTotalTime();
    Assertions.assertTrue(task1CurrenTime == project.getTotalTime());

    task2.startNewInterval();
    Thread.sleep(timerClock * 2);
    task2.stopActualInterval();
    long task2CurrentTime = task2.getTotalTime();

    Assertions.assertTrue(task1CurrenTime + task2CurrentTime == project.getTotalTime());
  }

  @Test
  public void startCountingTimeMultipleProjectsAndTaskTest() throws Exception {
    Task task1 = new Task("task1", project);
    Project project1 = new Project("project1", project);
    final Task task2 = new Task("task2", project1);

    Assertions.assertTrue(project.getComponents().size() == 2);
    Assertions.assertTrue(project1.getTotalTime() == 0);

    task1.startNewInterval();
    task2.startNewInterval();
    Thread.sleep(timerClock);
    task2.stopActualInterval();

    long project1TotalTime = project1.getTotalTime();
    Assertions.assertTrue(task2.getTotalTime() == project1TotalTime);

    Thread.sleep(timerClock * 2);
    task1.stopActualInterval();

    Assertions.assertTrue(task2.getTotalTime() == project1TotalTime);
    Assertions.assertTrue(task1.getTotalTime() + project1TotalTime == project.getTotalTime(),
            "projectTotalTime: " + project.getTotalTime() + " sum: "
                    + (task1.getTotalTime() + project1TotalTime));
  }
}
