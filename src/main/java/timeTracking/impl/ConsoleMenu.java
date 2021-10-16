package timeTracking.impl;

import timeTracking.api.MenuInterface;
import timeTracking.core.Component;
import timeTracking.core.Project;
import timeTracking.core.Task;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class ConsoleMenu implements MenuInterface {
  private List<Component> componentList;
  private Project actualProject;
  private Task actualTask;
  private String readFromJson;
  private String storeToJson;

  public ConsoleMenu(String readFromJson, String storeToJson) {
    this.readFromJson = readFromJson;
    this.storeToJson = storeToJson;
  }

  public ConsoleMenu(String readFromJson) {
    new ConsoleMenu(readFromJson,readFromJson);
  }

  @Override
  public Project createNewProject(String name) {
    System.out.println("Creating new project and swiching to add");
    actualProject = new Project(name,actualProject);
    componentList.add(actualProject);
    return actualProject;
  }

  @Override
  public void createTask(String name) {
    System.out.println("creating new task with name: " + name + " and appending to the project");
    actualTask = new Task(name, actualProject);
    actualProject.add(actualTask);
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
  public void start() throws Exception{
    System.out.println("starting");
    System.out.println("checking for json: " + readFromJson);
    boolean exists = checkForJson();

    if (!exists) {
      System.out.println("creating new empty project");
      actualProject = new Project("root", null);
    }

    printMenuOptions();
    Scanner reader = new Scanner(System.in);
    boolean done = false;
    while (!done){
      switch (reader.nextInt()) {
        case 1:
          System.out.println("type the project name: ");
          createNewProject(reader.nextLine());
          break;
        case 2:
          System.out.println("type the project name: ");
          createTask(reader.nextLine());
          break;
        case 3:
        case 4:
          System.out.println("to do...");
          break;
        case 5:
          long time = actualTask.getTotalTime();
          System.out.println("The time is: " + String.valueOf(time) + " seconds");
          System.out.println("started time: " + actualTask.getStartedTime().toString());
          Date actualTaskEndedTime = actualTask.getEndedTime();
          if (actualTaskEndedTime == null) {
            System.out.println("still working at it");
          }
          else {
            System.out.println("ended time" + actualTask.getEndedTime().toString());
          }
        default:
          done = true;
          break;
      }
    }
  }

  private void printMenuOptions() {
    System.out.println("choose option");
    System.out.println("1. Create Project");
    System.out.println("2. Create task");
    System.out.println("3. Store at json");
    System.out.println("4. Load from another json");
    System.out.println("5. Print current task time");
    System.out.println("6. Print current project time");
  }

  private boolean checkForJson() throws Exception {
    File jsonToReadFrom = new File(readFromJson);
    if (!jsonToReadFrom.exists()) {
      System.out.println("Json does not exists, a new one with name: " + readFromJson + " will be created");
      jsonToReadFrom.createNewFile();
      return false;
    }
    return true;
  }

  @Override
  public void exit() {

  }
}
