package timeTracking.api;

import timeTracking.core.Project;

public interface MenuInterface {
  Project createNewProject(String name);

  void createTask(String name);

  void changeProject();

  long getTaskTime();

  void returnToMenu();

  void addProjectToCurrentOne();

  boolean saveToJson();

  void loadFromJson(String fileName);

  void start() throws Exception;

  void exit();
}
