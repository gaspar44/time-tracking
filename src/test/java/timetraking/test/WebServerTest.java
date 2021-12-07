package timetraking.test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import timetracking.firtsmilestone.core.*;
import timetracking.firtsmilestone.impl.DemoTree;
import timetracking.firtsmilestone.impl.JsonKeys;

public class WebServerTest {
  private static final int port = 8080;
  private static final String host = "localhost";
  private static final String getTree = "get_tree";
  private static final String stop = "stop";
  private static final String start = "start";
  private static final String resetDemo = "reset_demo";
  private static final long clock = 2000;
  private final DemoTree demoTree = new DemoTree();

  @AfterEach
  public void reset() throws Exception {
    HttpRequest request = templateRequest(resetDemo, 0);
    HttpClient client = HttpClient.newHttpClient();
    client.send(request, HttpResponse.BodyHandlers.ofString());
  }

  @Test
  public void getRootElementTest() throws Exception {
    int idToSearch = 0;

    HttpRequest httpRequest = templateRequest(getTree, idToSearch);

    HttpClient client = HttpClient.newHttpClient();
    HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
    Component foundComponent = demoTree.getRootProject().findComponentById(idToSearch);
    String foundComponentJsonString = foundComponent.toJson(1).toString();
    foundComponentJsonString = foundComponentJsonString + "\n"; // due to HTTP empty line

    Assertions.assertEquals(200, response.statusCode());
    Assertions.assertEquals(foundComponentJsonString, response.body());
  }

  @Test
  public void startTaskTest() throws Exception {
    int idToSearch = demoTree.getTransPortation().getId();
    HttpRequest httpRequest = templateRequest(getTree, idToSearch);

    HttpClient client = HttpClient.newHttpClient();
    HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

    Assertions.assertNotNull(response.body());
    Assertions.assertEquals(200, response.statusCode());
    Task taskBeforeStart = (Task) jsonParser(response.body());

    Assertions.assertNotNull(taskBeforeStart);
    Assertions.assertEquals(0, taskBeforeStart.getTimeIntervalList().size());
    Assertions.assertNull(taskBeforeStart.getEndedTime());
    Assertions.assertNull(taskBeforeStart.getStartedTime());
    Assertions.assertEquals(0, taskBeforeStart.getTotalTime());
    Assertions.assertNull(taskBeforeStart.getTimeInterval());

    httpRequest = templateRequest(start, idToSearch);
    response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

    Assertions.assertEquals(200, response.statusCode());

    Thread.sleep(clock * 2L);

    httpRequest = templateRequest(getTree, idToSearch);
    response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

    Assertions.assertNotNull(response.body());
    Assertions.assertEquals(200, response.statusCode());

    Task taskAfterStart = (Task) jsonParser(response.body());

    Assertions.assertNotNull(taskAfterStart);
    Assertions.assertEquals(taskAfterStart.getName(), taskBeforeStart.getName());
    Assertions.assertTrue(taskAfterStart.getTotalTime() > taskBeforeStart.getTotalTime());
    Assertions.assertNotNull(taskAfterStart.getStartedTime());
    Assertions.assertNotNull(taskAfterStart.getEndedTime());
    Assertions.assertEquals(1, taskAfterStart.getTimeIntervalList().size());

    httpRequest = templateRequest(stop, idToSearch);
    client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
  }

  private HttpRequest templateRequest(String endPoint, int componentId) throws Exception {
    return  HttpRequest.newBuilder()
        .uri(new URI("http://" + host + ":" + port + "/" + endPoint + "?" + componentId))
        .GET()
        .build();
  }

  private Component jsonParser(String jsonToParse) throws Exception {
    JSONObject unparsedJsonObject = new JSONObject(jsonToParse);
    if (unparsedJsonObject.get(JsonKeys.TYPE_KEY).equals(JsonKeys.PROJECT_TYPE)) {
      parseJsonElementAsProject(unparsedJsonObject);
    } else if (unparsedJsonObject.get(JsonKeys.TYPE_KEY).equals(JsonKeys.TASK_TYPE)) {
      return parseJsonElementAsTask(unparsedJsonObject);
    }
    return null;
  }

  private Task parseJsonElementAsTask(JSONObject unparsedJsonObject) {
    Task task;
    String taskName = unparsedJsonObject.getString(JsonKeys.NAME_KEY);
    Project mockFather = new Project("mock",null);
    task = new Task(taskName, mockFather);
    task.setTotalTime(unparsedJsonObject.getLong(JsonKeys.DURATION_KEY));
    JSONArray jsonArrayTimeIntervalList;

    try {
      jsonArrayTimeIntervalList = unparsedJsonObject.getJSONArray(JsonKeys.TIME_INTERVAL_KEY);
    } catch (Exception e) {
      return null;
    }

    List<TimeInterval> timeIntervalList = new ArrayList<>();
    for (int i = 0; i < jsonArrayTimeIntervalList.length(); i++) {

      JSONObject jsonTimeInterval = (JSONObject) jsonArrayTimeIntervalList.get(i);
      String startTime = jsonTimeInterval.getString(JsonKeys.START_TIME_KEY);
      String endTime = jsonTimeInterval.getString(JsonKeys.END_TIME_KEY);
      long currentDuration = jsonTimeInterval.getLong(JsonKeys.CURRENT_TIME_INTERVAL_DURATION);

      TimeInterval timeInterval = new TimeInterval(task);

      timeInterval.setDuration(currentDuration);
      timeInterval.setStartTime(LocalTime.parse(startTime));
      timeInterval.setEndTime(LocalTime.parse(endTime));
      timeIntervalList.add(timeInterval);
    }

    task.setTimeIntervalList(timeIntervalList);
    return task;
  }

  private Project parseJsonElementAsProject(JSONObject unparsedObject) {

    Project project;
    String projectName = unparsedObject.getString(JsonKeys.NAME_KEY);
    project = new Project(projectName, null);

    project.setTotalTime(unparsedObject.getLong(JsonKeys.DURATION_KEY));
    try {
      String startTime = unparsedObject.getString(JsonKeys.START_TIME_KEY);
      String endTime = unparsedObject.getString(JsonKeys.END_TIME_KEY);
      project.setStartTime(LocalTime.parse(startTime));
      project.setEndTime(LocalTime.parse(endTime));
    } catch (Exception e) {
      e.getMessage();
    }

    List<String> tags = new ArrayList<>();

    try {
      JSONArray unparsedTags = unparsedObject.getJSONArray(JsonKeys.TAGS_KEY);

      for (int i = 0; i < unparsedTags.length(); i++) {
        tags.add(unparsedTags.getString(i));
      }

      project.setTags(tags);

    } catch (Exception e) {
      return null;
    }

    return project;

  }
}