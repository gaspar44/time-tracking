package timeTracking.impl;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import timeTracking.api.Visitor;
import timeTracking.core.Component;
import timeTracking.core.Project;
import timeTracking.core.Task;
import timeTracking.core.TimeInterval;

import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class JsonParser implements Visitor {
  private static JsonParser instance;
  private String fileName;
  private JSONArray projectTree;
  private JSONObject rootJsonProject;

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

  public static JsonParser getInstance() {
    if (instance == null){
      instance = new JsonParser();
    }
    return instance;
  }

  private JsonParser() {
    projectTree = new JSONArray();
    rootJsonProject = new JSONObject();
  }

  public List<Component> getProjectsFromJson(String fileName) throws Exception{
    List<Component> componentsToRet = new ArrayList<>();
    String stringProjectTree = Files.readString(Paths.get(fileName));
    JSONTokener tokener = new JSONTokener(stringProjectTree);
    JSONArray jsonArrayProjectTree = new JSONArray(tokener);

    for (int i = 0; i < jsonArrayProjectTree.length(); i++) {
      Component component = getProjectOrComponent(jsonArrayProjectTree,null);
      componentsToRet.add(component);
    }

    return componentsToRet;
  }

  private Component getProjectOrComponent(JSONArray jsonArrayProjectTree,Component father) {
    for (int i = 0; i < jsonArrayProjectTree.length(); i++ ){
      JSONObject unparsedObject = (JSONObject) jsonArrayProjectTree.get(i);

      if (unparsedObject.get(TYPE_KEY).equals(PROJECT_TYPE)) {
        return parseProject(unparsedObject,father);
      }

      else if (unparsedObject.get(TYPE_KEY).equals(TASK_TYPE)) {
        return parseTask(unparsedObject,father);
      }

    }
    return null;
  }

  private Task parseTask(JSONObject unparsedObject, Component father) {
    Task task;
    String taskName = unparsedObject.getString(NAME_KEY);
    task = new Task(taskName, (Project) father);
    task.setTotalTime(unparsedObject.getLong(DURATION_KEY));
    JSONArray jsonArrayTimeIntervalList;

    try {
      jsonArrayTimeIntervalList = unparsedObject.getJSONArray(TIME_INTERVAL_KEY);
    }

    catch (Exception e) {
      e.printStackTrace();
      return null;
    }

    List<TimeInterval> timeIntervalList = new ArrayList<>();
    for (int i = 0; i < jsonArrayTimeIntervalList.length(); i ++) {

      JSONObject jsonTimeInterval = (JSONObject) jsonArrayTimeIntervalList.get(i);
      String startTime =  jsonTimeInterval.getString(START_TIME_KEY);
      String endTime = jsonTimeInterval.getString(END_TIME_KEY);
      long currentDuration = jsonTimeInterval.getLong(CURRENT_TIME_INTERVAL_DURATION);

      TimeInterval timeInterval = new TimeInterval(task);

      timeInterval.setDuration(currentDuration);
      timeInterval.setStartTime(LocalTime.parse(startTime) );
      timeInterval.setEndTime(LocalTime.parse(endTime) );
      timeIntervalList.add(timeInterval);
    }

    task.setTimeIntervalList(timeIntervalList);
    return task;
  }

  private Project parseProject(JSONObject unparsedObject,Component father) {
    Project project;
    String projectName = unparsedObject.getString(NAME_KEY);
    project = new Project(projectName, (Project) father);

    project.setTotalTime(unparsedObject.getLong(DURATION_KEY));
    JSONArray components;

    try {
      components = (JSONArray) unparsedObject.get(COMPONENT_KEY);
    }
    catch (Exception e) {
      return project;
    }


    getProjectOrComponent(components,project);

    return project;

  }

  public List<Component> getProjectsFromJson() throws Exception {
    return getProjectsFromJson(fileName);
  }

  public boolean storeProjectsIntoJson(String storeToJson) {
    try {
      FileWriter writer = new FileWriter(storeToJson);
      writer.write(projectTree.toString(4));

      writer.flush();
      writer.close();
    }

    catch (Exception e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  @Override
  public void visitTask(Task task) {

    JSONObject jsonObject = new JSONObject();
    jsonObject.put(NAME_KEY,task.getName());
    jsonObject.put(FATHER_NAME,task.getFather().getName());
    jsonObject.put(TYPE_KEY,"Task");
    jsonObject.put(DURATION_KEY,task.getTotalTime());
    JSONArray timeIntervals = new JSONArray();

    List<TimeInterval> timeIntervalList = task.getTimeIntervalList();

    for (TimeInterval timeInterval: timeIntervalList) {
      JSONObject jsonTimeInterval = new JSONObject();
      jsonTimeInterval.put(START_TIME_KEY,timeInterval.getStartTime());
      jsonTimeInterval.put(END_TIME_KEY,timeInterval.getEndTime());
      jsonTimeInterval.put(CURRENT_TIME_INTERVAL_DURATION,timeInterval.getCurrentDuration());
      timeIntervals.put(jsonTimeInterval);
    }

    jsonObject.put(TIME_INTERVAL_KEY,timeIntervals);

    JSONArray components = rootJsonProject.getJSONArray(COMPONENT_KEY);
    components.put(jsonObject);
  }

  @Override
  public void visitProject(Project project) {
    List<Component> components = project.getComponents();
    JSONArray jsonArray;
    JSONObject jsonObject = new JSONObject();

    if (project.getFather() != null ){
      jsonObject.put(FATHER_NAME,project.getFather().getName());
      jsonObject.put(NAME_KEY,project.getName());
      jsonObject.put(TYPE_KEY,PROJECT_TYPE);
      jsonObject.put(DURATION_KEY,project.getTotalTime());

      if (components.size() != 0) {
        jsonArray = new JSONArray();
        jsonObject.put(COMPONENT_KEY,jsonArray);
      }

      JSONArray fatherJsonArray = rootJsonProject.getJSONArray(COMPONENT_KEY);
      fatherJsonArray.put(jsonObject);
    }

    else {
      jsonObject.put(NAME_KEY,project.getName());
      jsonObject.put(TYPE_KEY,PROJECT_TYPE);
      jsonObject.put(DURATION_KEY,project.getTotalTime());

      jsonArray = new JSONArray();
      jsonObject.put(COMPONENT_KEY,jsonArray);
      projectTree.put(jsonObject);
    }

    rootJsonProject = jsonObject;

    for (Component component: components) {
      component.acceptVisitor(this);
    }

    storeProjectsIntoJson(fileName);
  }
}
