package timeTracking.impl;

import timeTracking.core.Project;
import timeTracking.api.MenuInterface;

public class ConsoleMenu implements MenuInterface {
  @Override
  public Project createNewProject() {
    return null;
  }

  @Override
  public void createTask() {

  }

  @Override
  public void changeProject() {

  }

  @Override
  public long getTaskTime() {
    return 0;
  }

  @Override
  public void returnToMenu() {

  }

  @Override
  public void addProjectToCurrentOne() {

  }

  @Override
  public boolean saveToJson() {
    return false;
  }

  @Override
  public void loadFromJson(String fileName) {

  }

  @Override
  public void start() {

  }

  @Override
  public void exit() {

  }
}
