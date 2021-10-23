package timeTracking.impl;

import timeTracking.api.Visitor;
import timeTracking.core.Component;
import timeTracking.core.Project;
import timeTracking.core.Task;

public class JsonParser implements Visitor {
  private static JsonParser instance;
  private String fileName;

  public static JsonParser getInstance() {
    if (instance == null){
      instance = new JsonParser();
    }
    return instance;
  }

  public Component getProjectsFromJson(String fileName) {
    return null;
  }

  public Component getProjectsFromJson() {
    return getProjectsFromJson(fileName);
  }

  public boolean storeProjectsIntoJson(String storeToJson) {
    return true;
  }

  public boolean storeProjectsIntoJson() {
    return storeProjectsIntoJson(fileName);
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  @Override
  public void visitTask(Task task) {

  }

  @Override
  public void visitProject(Project project) {

  }
}
