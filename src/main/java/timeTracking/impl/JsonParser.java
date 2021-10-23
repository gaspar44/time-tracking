package timeTracking.impl;

import org.json.JSONArray;
import org.json.JSONObject;
import timeTracking.api.Visitor;
import timeTracking.core.Component;
import timeTracking.core.Project;
import timeTracking.core.Task;
import timeTracking.core.TimeInterval;

import java.io.FileWriter;
import java.util.List;

public class JsonParser implements Visitor {
  private static JsonParser instance;
  private String fileName;
  private JSONArray projectTree;
  private JSONObject actualJsonObject;

  public static JsonParser getInstance() {
    if (instance == null){
      instance = new JsonParser();
    }
    return instance;
  }

  private JsonParser() {
    projectTree = new JSONArray();
  }

  public List<Component> getProjectsFromJson(String fileName) {
    return null;
  }

  public List<Component> getProjectsFromJson() {
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
    jsonObject.put("name",task.getName());
    jsonObject.put("father_name",task.getFather().getName());
    jsonObject.put("type","Task");
    jsonObject.put("duration",task.getTotalTime());
    JSONArray timeIntervals = new JSONArray();

    List<TimeInterval> timeIntervalList = task.getTimeIntervalList();

    for (TimeInterval timeInterval: timeIntervalList) {
      JSONObject jsonTimeInterval = new JSONObject();
      jsonTimeInterval.put("start_time",timeInterval.getStartTime());
      jsonTimeInterval.put("end_time",timeInterval.getEndTime());
      jsonTimeInterval.put("current_duration",timeInterval.getCurrentDuration());
      timeIntervals.put(jsonTimeInterval);
    }

    jsonObject.put("time_intervals",timeIntervals);

    JSONArray components = (JSONArray) actualJsonObject.get("components");
    components.put(jsonObject);
  }

  @Override
  public void visitProject(Project project) {
    List<Component> components = project.getComponents();
    JSONArray jsonArray = new JSONArray();
    actualJsonObject = new JSONObject();

    if (project.getFather() != null ){
      actualJsonObject.put("father_name",project.getFather().getName());
    }

    actualJsonObject.put("name",project.getName());
    actualJsonObject.put("type","Project");
    actualJsonObject.put("duration",project.getTotalTime());
    actualJsonObject.put("components",jsonArray);
    projectTree.put(actualJsonObject);

    for (Component component: components) {
      component.acceptVisitor(this);
    }

    storeProjectsIntoJson(fileName);
  }
}
