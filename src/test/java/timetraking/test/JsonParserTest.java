package timetraking.test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timetracking.firtsmilestone.core.Project;
import timetracking.firtsmilestone.core.Task;
import timetracking.firtsmilestone.core.Timer;
import timetracking.firtsmilestone.impl.JsonParser;



public class JsonParserTest {
  private static JsonParser jsonParser;
  private Project project;
  private Task task;
  private final long timerClock = Timer.getInstance().getTimerMillisecondsPeriod();

  @BeforeAll
  public static void beforeAll() throws Exception {
    jsonParser = JsonParser.getInstance();
  }

  @BeforeEach
  public void setup() throws Exception {
    project = new Project("root", null);
    task = new Task("task", project);
  }

  @Test
  public void acceptSimpleProjectVisitorTest() throws Exception {
    String jsonName = "demo.json";
    jsonParser.setFileName(jsonName);
    Project demoProject = new Project("root", null);
    Project demoProject1 = new Project("project1", demoProject);
    demoProject.acceptVisitor(jsonParser);

    Assertions.assertTrue(checkForJson(jsonName));
    String readedJson = new String(Files.readAllBytes(Paths.get(jsonName)));
    Assertions.assertTrue(isJsonvalid(readedJson));

  }

  @Test
  public void acceptSimpleProjectThreeLevelVisitorTest() throws Exception {
    String jsonName = "demo.json";
    jsonParser.setFileName(jsonName);
    Project demoProject = new Project("root", null);
    Project demoProject1 = new Project("project1", demoProject);
    final Project demoProject2 = new Project("project2", demoProject1);
    demoProject.acceptVisitor(jsonParser);

    Assertions.assertTrue(checkForJson(jsonName));
    String readedJson = new String(Files.readAllBytes(Paths.get(jsonName)));
    Assertions.assertTrue(isJsonvalid(readedJson));


    Project project = JsonParser.getInstance().getProjectsFromJson(jsonName);
    Assertions.assertEquals(1, project.getComponents().size());
    Assertions.assertEquals(project.getName(), demoProject.getName());
    Assertions.assertNull(project.getFather());
    Assertions.assertEquals(project.getFather(), demoProject.getFather());

    Project project1 = (Project) project.getComponents().get(0);
    Assertions.assertEquals(1, project1.getComponents().size());
    Assertions.assertEquals(demoProject1.getName(), project1.getName());
    Assertions.assertEquals(demoProject1.getFather().getName(), project1.getFather().getName());

    Project project2 = (Project) project1.getComponents().get(0);
    Assertions.assertEquals(0, project2.getComponents().size());
    Assertions.assertEquals(demoProject2.getName(), project2.getName());
    Assertions.assertEquals(demoProject2.getFather().getName(), project2.getFather().getName());
  }

  @Test
  public void acceptSimpleProjectWithStartedTaskVisitorTest() throws Exception {
    String jsonName = "demo.json";
    jsonParser.setFileName(jsonName);
    task.startNewInterval();
    Thread.sleep(timerClock);
    task.stopActualInterval();

    project.acceptVisitor(jsonParser);

    Assertions.assertTrue(checkForJson(jsonName));
    String readedJson = new String(Files.readAllBytes(Paths.get(jsonName)));
    Assertions.assertTrue(isJsonvalid(readedJson));
    Project loadedProject = JsonParser.getInstance().getProjectsFromJson(jsonName);
    Assertions.assertEquals(1, loadedProject.getComponents().size());
    Task taskOfLoadedProject = (Task) loadedProject.getComponents().get(0);

    Assertions.assertEquals(task.getName(), taskOfLoadedProject.getName());
    Assertions.assertEquals(task.getTimeIntervalList().size(),
            taskOfLoadedProject.getTimeIntervalList().size());
    Assertions.assertEquals(task.getStartedTime(), taskOfLoadedProject.getStartedTime());
    Assertions.assertEquals(task.getTotalTime(), taskOfLoadedProject.getTotalTime());
  }

  @Test
  public void acceptSimpleProjectWithMultipleTimeIntervalTaskVisitorTest() throws Exception {
    String jsonName = "demo.json";
    jsonParser.setFileName(jsonName);
    Task task2 = new Task("task2", project);
    task.startNewInterval();

    Thread.sleep(timerClock);
    task2.startNewInterval();
    task.stopActualInterval();

    task.startNewInterval();
    Thread.sleep(timerClock);
    task2.stopActualInterval();
    task.stopActualInterval();

    project.acceptVisitor(jsonParser);

    Assertions.assertTrue(checkForJson(jsonName));
    String readedJson = new String(Files.readAllBytes(Paths.get(jsonName)));

    Assertions.assertTrue(isJsonvalid(readedJson));
    Project loadedProject = jsonParser.getProjectsFromJson(jsonName);

    Assertions.assertEquals(2, loadedProject.getComponents().size());
    Assertions.assertNull(loadedProject.getFather());
    Assertions.assertEquals(project.getName(), loadedProject.getName());

    Task taskOfLoadedProjectWithTwoTimeIntervals = (Task) loadedProject.getComponents().get(0);

    Assertions.assertEquals(2,
            taskOfLoadedProjectWithTwoTimeIntervals.getTimeIntervalList().size());
    Assertions.assertEquals(task.getTotalTime(),
            taskOfLoadedProjectWithTwoTimeIntervals.getTotalTime());

    Task taskOfLoadedProjectWithOneTimeIntervals = (Task) loadedProject.getComponents().get(1);

    Assertions.assertEquals(1,
            taskOfLoadedProjectWithOneTimeIntervals.getTimeIntervalList().size());
    Assertions.assertEquals(task2.getTotalTime(),
            taskOfLoadedProjectWithOneTimeIntervals.getTotalTime());

  }

  private boolean checkForJson(String readFromJson) throws Exception {
    File jsonToReadFrom = new File(readFromJson);
    return jsonToReadFrom.exists();
  }

  public boolean isJsonvalid(String test) {
    try {
      new JSONObject(test);
    } catch (JSONException ex) {
      try {
        new JSONArray(test);
      } catch (JSONException ex1) {
        return false;
      }
    }
    return true;
  }
}
