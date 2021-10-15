package timeTracking.api;

import timeTracking.core.Project;

public interface MenuInterface {
  Project createNewProject();

  void createTask();

  void changeProject();

  long getTaskTime();

  void returnToMenu();

  void addProjectToCurrentOne();

  boolean saveToJson();

  void loadFromJson(String fileName);

  void start();

  void exit();
}
