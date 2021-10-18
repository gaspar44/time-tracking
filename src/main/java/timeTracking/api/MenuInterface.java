package timeTracking.api;

import timeTracking.core.Component;
import timeTracking.core.Project;
import timeTracking.core.Task;

import java.util.List;

public interface MenuInterface {
  Project createNewProject(String name);

  Task createTask(String name);

  List<Component> getComponentList();

  void changeProject(String filename);

  long getTaskTime();

  void returnToMenu();

  void addProjectToCurrentOne();

  boolean saveToJson();

  void loadFromJson(String fileName);

  void start() throws Exception;

  void exit();
}
