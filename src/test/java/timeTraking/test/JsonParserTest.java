package timeTraking.test;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timeTracking.core.Project;
import timeTracking.core.Task;
import timeTracking.impl.JsonParser;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonParserTest {
  private static JsonParser jsonParser;
  private Project project;
  private Task task;


  @BeforeAll
  public static void beforeAll() throws Exception {
    jsonParser = JsonParser.getInstance();
  }

/*  @AfterEach
  public static void teardown() throws Exception {

  }*/

  @BeforeEach
  public void setup() throws Exception {
    project = new Project("root",null);
    task = new Task("task",project);
  }

  @Test
  public void acceptSimpleProjectVisitorTest() throws Exception {
    String jsonName = "demo.json";
    jsonParser.setFileName(jsonName);
    Project demoProject = new Project("root",null);
    Project demoProject1 = new Project ("project1",demoProject);
    demoProject.acceptVisitor(jsonParser);

    Assertions.assertTrue(checkForJson(jsonName));
    String readedJson = new String(Files.readAllBytes(Paths.get(jsonName) ));
    Assertions.assertTrue(isJSONValid(readedJson));

  }

  @Test
  public void acceptSimpleProjectThreeLevelVisitorTest() throws Exception {
    String jsonName = "demo.json";
    jsonParser.setFileName(jsonName);
    Project demoProject = new Project("root",null);
    Project demoProject1 = new Project ("project1",demoProject);
    Project demoProject2 = new Project("project2",demoProject1);
    demoProject.acceptVisitor(jsonParser);

    Assertions.assertTrue(checkForJson(jsonName));
    String readedJson = new String(Files.readAllBytes(Paths.get(jsonName) ));
    Assertions.assertTrue(isJSONValid(readedJson));


    Project project = (Project) JsonParser.getInstance().getProjectsFromJson(jsonName).get(0);
    Assertions.assertEquals(1,project.getComponents().size());
    Assertions.assertEquals(project.getName(),demoProject.getName());
    Assertions.assertNull(project.getFather());
    Assertions.assertEquals(project.getFather(),demoProject.getFather());

    Project project1 = (Project) project.getComponents().get(0);
    Assertions.assertEquals(1,project1.getComponents().size());
    Assertions.assertEquals(demoProject1.getName(),project1.getName());
    Assertions.assertEquals(demoProject1.getFather().getName(),project1.getFather().getName());

    Project project2 = (Project) project1.getComponents().get(0);
    Assertions.assertEquals(0,project2.getComponents().size());
    Assertions.assertEquals(demoProject2.getName(),project2.getName());
    Assertions.assertEquals(demoProject2.getFather().getName(),project2.getFather().getName());
  }

  @Test
  public void acceptSimpleProjectWithStartedTaskVisitorTest() throws Exception {
    String jsonName = "demo.json";
    jsonParser.setFileName(jsonName);
    task.startNewInterval();
    Thread.sleep(2000);
    task.stopActualInterval();

    project.acceptVisitor(jsonParser);

    Assertions.assertTrue(checkForJson(jsonName));
    String readedJson = new String(Files.readAllBytes(Paths.get(jsonName) ));
    Assertions.assertTrue(isJSONValid(readedJson));

  }

  @Test
  public void acceptSimpleProjectWithMultipleTimeIntervalTaskVisitorTest() throws Exception {
    String jsonName = "demo.json";
    jsonParser.setFileName(jsonName);
    task.startNewInterval();
    Thread.sleep(2000);
    task.stopActualInterval();

    task.startNewInterval();
    Thread.sleep(2000);
    task.stopActualInterval();

    project.acceptVisitor(jsonParser);

    Assertions.assertTrue(checkForJson(jsonName));
    String readedJson = new String(Files.readAllBytes(Paths.get(jsonName) ));
    Assertions.assertTrue(isJSONValid(readedJson));
    jsonParser.getProjectsFromJson(jsonName);

  }

  private boolean checkForJson(String readFromJson) throws Exception {
    File jsonToReadFrom = new File(readFromJson);
    if (!jsonToReadFrom.exists()) {
      return false;
    }
    return true;
  }

  public boolean isJSONValid(String test) {
    try {
      new JSONObject(test);
    }
    catch (JSONException ex) {
      try {
        new JSONArray(test);
      }
      catch (JSONException ex1) {
        return false;
      }
    }
    return true;
  }
}
