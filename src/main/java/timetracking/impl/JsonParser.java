package timetracking.impl;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import timetracking.api.Visitor;
import timetracking.core.Component;
import timetracking.core.Project;
import timetracking.core.Task;
import timetracking.core.TimeInterval;

import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class JsonParser implements Visitor {
  private static final String TIME_INTERVAL_KEY = "time_intervals";
  private static final String CURRENT_TIME_INTERVAL_DURATION = "current_duration";
  private static final String DURATION_KEY = "duration";
  private static final String NAME_KEY = "name";
  private static final String TYPE_KEY = "type";
  private static final String COMPONENT_KEY = "components";
  private static final String START_TIME_KEY = "start_time";
  private static final String END_TIME_KEY = "end_time";
  private static final String FATHER_NAME = "father_name";
  private static final String PROJECT_TYPE = "Project";
  private static final String TASK_TYPE = "Task";
  private static JsonParser instance;
  private String fileName;
  private JSONArray projectTree;
  private JSONObject rootJsonProject;
  private Project parsedTreeFromFile;

  private JsonParser() {
    projectTree = new JSONArray();
    rootJsonProject = new JSONObject();
  }

  public static JsonParser getInstance() {
    if (instance == null) {
      instance = new JsonParser();
    }
    return instance;
  }

  public Project getProjectsFromJson(String fileName) throws Exception {
    String stringProjectTree = Files.readString(Paths.get(fileName));

    // tokener let the array to transform the string into a JSONArrayObject.
    JSONTokener tokener = new JSONTokener(stringProjectTree);
    JSONArray jsonArrayProjectTree = new JSONArray(tokener);

    for (int i = 0; i < jsonArrayProjectTree.length(); i++) {
      transformJsonArrayIntoProject(jsonArrayProjectTree, null);
    }

    return parsedTreeFromFile;
  }

  private void transformJsonArrayIntoProject(JSONArray jsonArrayProjectTree, Component father) {
    for (int i = 0; i < jsonArrayProjectTree.length(); i++) {
      JSONObject unparsedJsonObject = (JSONObject) jsonArrayProjectTree.get(i);

      if (unparsedJsonObject.get(TYPE_KEY).equals(PROJECT_TYPE)) {
        parseJsonElementAsProject(unparsedJsonObject, father);
      } else if (unparsedJsonObject.get(TYPE_KEY).equals(TASK_TYPE)) {
        parseJsonElementAsTask(unparsedJsonObject, father);
      }
    }
  }

  private void parseJsonElementAsTask(JSONObject unparsedJsonObject, Component father) {
    Task task;
    String taskName = unparsedJsonObject.getString(NAME_KEY);
    task = new Task(taskName, (Project) father);
    task.setTotalTime(unparsedJsonObject.getLong(DURATION_KEY));
    JSONArray jsonArrayTimeIntervalList;

    try {
      jsonArrayTimeIntervalList = unparsedJsonObject.getJSONArray(TIME_INTERVAL_KEY);
    } catch (Exception e) {
      return;
    }

    List<TimeInterval> timeIntervalList = new ArrayList<>();
    for (int i = 0; i < jsonArrayTimeIntervalList.length(); i++) {

      JSONObject jsonTimeInterval = (JSONObject) jsonArrayTimeIntervalList.get(i);
      String startTime = jsonTimeInterval.getString(START_TIME_KEY);
      String endTime = jsonTimeInterval.getString(END_TIME_KEY);
      long currentDuration = jsonTimeInterval.getLong(CURRENT_TIME_INTERVAL_DURATION);

      TimeInterval timeInterval = new TimeInterval(task);

      timeInterval.setDuration(currentDuration);
      timeInterval.setStartTime(LocalTime.parse(startTime));
      timeInterval.setEndTime(LocalTime.parse(endTime));
      timeIntervalList.add(timeInterval);
    }

    task.setTimeIntervalList(timeIntervalList);
  }

  private void parseJsonElementAsProject(JSONObject unparsedObject, Component father) {
    Project project;
    String projectName = unparsedObject.getString(NAME_KEY);
    project = new Project(projectName, (Project) father);

    project.setTotalTime(unparsedObject.getLong(DURATION_KEY));
    try {
      String startTime = unparsedObject.getString(START_TIME_KEY);
      String endTime = unparsedObject.getString(END_TIME_KEY);
      project.setStartTime(LocalTime.parse(startTime));
      project.setEndTime(LocalTime.parse(endTime));
    } catch (Exception e) {
      System.out.println("Project without started task " + projectName);
    }

    JSONArray components;

    try {
      components = (JSONArray) unparsedObject.get(COMPONENT_KEY);
    } catch (Exception e) {
      return;
    }

    if (father == null) {
      parsedTreeFromFile = project;
    }
    transformJsonArrayIntoProject(components, project);
  }

  private boolean storeProjectsIntoJson(String storeToJson, Project project) {
    /*This must be done because we want to write from the root until the end
    we don't want to write from some nodes, we want from the start.
    This grants a way to end the recursion.
    */
    if (project.getFather() != null) {
      return true;
    }

    try {
      if (!projectTree.isEmpty()) {
        FileWriter writer = new FileWriter(storeToJson, false);
        writer.write(projectTree.toString(4));

        writer.flush(); //We make sure that Project Tree is correctly written to the Json file.
        writer.close();
      } else {
        return false;
      }
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }

    /*This is done to reset the actual project. If this is not made,
     the project will be added as a subproject of itself.
    */

    rootJsonProject = new JSONObject();
    projectTree = new JSONArray();

    return true;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  @Override
  public void visitTask(Task task) {

    JSONObject jsonObject = new JSONObject();
    jsonObject.put(NAME_KEY, task.getName());
    jsonObject.put(FATHER_NAME, task.getFather().getName());
    jsonObject.put(TYPE_KEY, "Task");
    jsonObject.put(DURATION_KEY, task.getTotalTime());
    JSONArray timeIntervals = new JSONArray();

    List<TimeInterval> timeIntervalList = task.getTimeIntervalList();

    for (TimeInterval timeInterval : timeIntervalList) {
      JSONObject jsonTimeInterval = new JSONObject();
      jsonTimeInterval.put(START_TIME_KEY, timeInterval.getStartTime());
      jsonTimeInterval.put(END_TIME_KEY, timeInterval.getEndTime());
      jsonTimeInterval.put(CURRENT_TIME_INTERVAL_DURATION, timeInterval.getCurrentDuration());
      timeIntervals.put(jsonTimeInterval);
    }

    jsonObject.put(TIME_INTERVAL_KEY, timeIntervals);

    JSONArray components = rootJsonProject.getJSONArray(COMPONENT_KEY);
    components.put(jsonObject);
  }

  @Override
  public void visitProject(Project project) {
    List<Component> components = project.getComponents();
    JSONArray jsonArray;
    JSONObject jsonObject = new JSONObject();

    jsonObject.put(NAME_KEY, project.getName());
    jsonObject.put(TYPE_KEY, PROJECT_TYPE);
    jsonObject.put(DURATION_KEY, project.getTotalTime());
    jsonObject.put(START_TIME_KEY, project.getStartedTime());
    jsonObject.put(END_TIME_KEY, project.getEndedTime());

    if (project.getFather() != null) {
      jsonObject.put(FATHER_NAME, project.getFather().getName());

      jsonArray = new JSONArray();
      jsonObject.put(COMPONENT_KEY, jsonArray);

      JSONArray fatherJsonArray = rootJsonProject.getJSONArray(COMPONENT_KEY);
      fatherJsonArray.put(jsonObject);
    } else {
      jsonArray = new JSONArray();
      jsonObject.put(COMPONENT_KEY, jsonArray);
      projectTree.put(jsonObject);
    }
    /*
    This is done because we are going thought recursive iteration
    if we don't set a previous object, when we return to this scope ,
    will lose the previous state, this causes that some projects
    can be converted into child of their brothers.
    This behaviour is unexpected
    */

    JSONObject previous = rootJsonProject;
    rootJsonProject = jsonObject;

    for (Component component : components) {
      component.acceptVisitor(this);
    }

    rootJsonProject = previous;
    storeProjectsIntoJson(fileName, project);
  }
}
