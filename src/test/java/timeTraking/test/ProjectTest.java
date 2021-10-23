package timeTraking.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timeTracking.core.Project;
import timeTracking.core.Task;

public class ProjectTest {
  private Project project;

  @BeforeEach
  public void setup() throws Exception {
    project = new Project("demo",null);
  }

  @Test
  public void addTaskToProjectTest() throws Exception{
    Assertions.assertTrue(project.getComponents().size() == 0);
    Assertions.assertTrue(project.getTotalTime() == 0);

    Task task = new Task("holis",project);
    Assertions.assertTrue(project.getComponents().size() == 1);
  }

  @Test
  public void startCountingTimeAtProject() throws Exception {
    Task task = new Task("holis",project);
    Assertions.assertTrue(project.getComponents().size() == 1);
    Assertions.assertTrue(project.getTotalTime() == 0);
    task.startNewInterval();
    Thread.sleep(1000);

    Assertions.assertTrue(project.getTotalTime() > 0);

    task.stopActualInterval();
    long currentDuration = task.getTotalTime();
    Thread.sleep(2000);
    Assertions.assertEquals(currentDuration,project.getTotalTime());
  }

  @Test
  public void startCountingTimeMultipleTaskTest() throws Exception {
    Task task1 = new Task("holis",project);
    Task task2 = new Task("holis2",project);

    Assertions.assertTrue(project.getComponents().size() == 2);
    Assertions.assertTrue(project.getTotalTime() == 0);

    task1.startNewInterval();
    Thread.sleep(1000);

    task1.stopActualInterval();
    long task1CurrenTime = task1.getTotalTime();
    Assertions.assertTrue(task1CurrenTime == project.getTotalTime());

    task2.startNewInterval();
    Thread.sleep(2000);
    task2.stopActualInterval();
    long task2CurrentTime = task2.getTotalTime();

    Assertions.assertTrue(task1CurrenTime + task2CurrentTime == project.getTotalTime());
  }

  @Test
  public void startCountingTimeMultipleProjectsAndTaskTest() throws Exception {
    Task task1 = new Task("task1",project);
    Project project1 = new Project("project1",project);
    Task task2 = new Task("task2",project1);

    Assertions.assertTrue(project.getComponents().size() == 2);
    Assertions.assertTrue(project1.getTotalTime() == 0);

    task1.startNewInterval();
    task2.startNewInterval();
    Thread.sleep(1000);
    task2.stopActualInterval();

    long project1TotalTime = project1.getTotalTime();
    Assertions.assertTrue(task2.getTotalTime() == project1TotalTime);

    Thread.sleep(2000);
    task1.stopActualInterval();

    Assertions.assertTrue(task2.getTotalTime() == project1TotalTime);
    Assertions.assertTrue(task1.getTotalTime() + project1TotalTime == project.getTotalTime(),"projectTotalTime: " + project.getTotalTime() + " sum: " + (task1.getTotalTime() + project1TotalTime));
  }
}