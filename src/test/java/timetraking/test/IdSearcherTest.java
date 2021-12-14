package timetraking.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import timetracking.firtsmilestone.core.Component;
import timetracking.firtsmilestone.core.Project;
import timetracking.firtsmilestone.core.Task;
import timetracking.firtsmilestone.impl.DemoTree;

public class IdSearcherTest {
  private static DemoTree demoTree;

  @BeforeAll
  static void setup() {
    demoTree = new DemoTree();
  }

  @Test
  public void searchForIdAtTreeFirstLevelTest() {
    Project database = demoTree.getDataBase();
    int expectedId = database.getId();
    Component foundComponent = demoTree.getRootProject().findComponentById(expectedId);
    Assertions.assertEquals(database, foundComponent);
  }

  @Test
  public void searchForIdAtTreeSecondLevelTest() {
    Project problems = demoTree.getProblems();
    int expectedId = problems.getId();
    Component foundComponent = demoTree.getRootProject().findComponentById(expectedId);
    Assertions.assertEquals(foundComponent, problems);
  }

  @Test
  public void searchForIdAtTreeThirdLevelTest() {
    Task firstMilestone = demoTree.getFirstMilestone();
    int expectedId = firstMilestone.getId();
    Component foundComponent = demoTree.getRootProject().findComponentById(expectedId);
    Assertions.assertEquals(foundComponent, firstMilestone);
  }

  @Test
  public void searchForNonExistingIdTest() {
    int id = 39;
    Component foundComponent = demoTree.getRootProject().findComponentById(id);
    Assertions.assertNull(foundComponent);
  }

  @Test
  public void searchForTaskTest() {
    Task transPortation = demoTree.getTransportation();
    int expectedId = transPortation.getId();
    Component foundComponent = demoTree.getRootProject().findComponentById(expectedId);
    Assertions.assertEquals(foundComponent, transPortation);
  }
}
