package timeTraking.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timeTracking.api.MenuInterface;
import timeTracking.core.Project;
import timeTracking.core.Task;
import timeTracking.impl.ConsoleMenu;

public class ConsoleMenuTest {
  private static MenuInterface menu;
  private String projectName = "project";
  private String taskName = "task";

  @BeforeEach
  public void setup() throws Exception {
    menu = new ConsoleMenu();
  }

  @Test
  public void createProjectTest() throws Exception {
    Project project = menu.createNewProject(projectName);
    Assertions.assertNotNull(project);
    Project rootProject = menu.getRootProject();

    Assertions.assertEquals(1,rootProject.getComponents().size());
    Assertions.assertEquals(project, rootProject.getComponents().get(0));
  }

  @Test
  public void createTaskAtProjectTest() throws Exception {
    Project project = menu.createNewProject(projectName);
    Task task = menu.createTask(taskName);

    Assertions.assertNotNull(task);
    Assertions.assertEquals(1,project.getComponents().size());
    Assertions.assertEquals(task,project.getComponents().get(0));
  }
}
