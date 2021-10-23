package timeTraking.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timeTracking.api.MenuInterface;
import timeTracking.core.Component;
import timeTracking.core.Project;
import timeTracking.core.Task;
import timeTracking.impl.ConsoleMenu;

import java.util.List;

public class ConsoleMenuTest {
  private static MenuInterface menu;
  private String projectName = "project";
  private String taskName = "task";

  @BeforeEach
  public void setup() throws Exception {
    menu = new ConsoleMenu(null,null);
  }

  @Test
  public void createProjectTest() throws Exception {
    List<Component> components = menu.getComponentList();
    Assertions.assertEquals(0,components.size());

    Project project = menu.createNewProject(projectName);
    Assertions.assertNotNull(project);
    components = menu.getComponentList();

    Assertions.assertEquals(1,components.size());
    Assertions.assertEquals(project, components.get(0));
  }

  @Test
  public void createTaskAtProjectTest() throws Exception {
    menu.createNewProject(projectName);
    Project project = (Project) menu.getComponentList().get(0);

    Task task = menu.createTask(taskName);

    Assertions.assertNotNull(task);
    Assertions.assertEquals(1,project.getComponents().size());
    Assertions.assertEquals(task,project.getComponents().get(0));
  }
}
