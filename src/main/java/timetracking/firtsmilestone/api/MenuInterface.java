package timetracking.firtsmilestone.api;

import timetracking.firtsmilestone.core.Project;

public interface MenuInterface {
  void createNewProject(String name);

  void createTask(String name);

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
