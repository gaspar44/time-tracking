package timetracking.api;

import timetracking.core.Project;
import timetracking.core.Task;

public interface MenuInterface {
  Project createNewProject(String name);

  Task createTask(String name);

  Project getRootProject();

  void changeProject(String filename);

  long getTaskTime();

  void returnToMenu();

  void addProjectToCurrentOne();

  boolean saveToJson(String fileName);

  boolean loadFromJson(String fileName);

  void start() throws Exception;

  boolean printTree();

}
