package timeTracking.impl;

import timeTracking.api.MenuInterface;
import timeTracking.core.Component;
import timeTracking.core.Project;
import timeTracking.core.Task;
import timeTracking.core.JsonParser;

import java.io.File;
import java.util.*;

public class ConsoleMenu implements MenuInterface {
  private List<Component> componentList;
  private Project actualProject;
  private Task actualTask;
  private String readFromJson;
  private String storeToJson;
  private static JsonParser jsonParser;

  public ConsoleMenu(String readFromJson, String storeToJson) {
    this.readFromJson = readFromJson;
    this.storeToJson = storeToJson;
    componentList = new ArrayList<>();
    jsonParser = JsonParser.getInstance();
  }

  public ConsoleMenu(String readFromJson) {
    new ConsoleMenu(readFromJson,readFromJson);
  }


  @Override
  public List<Component> getComponentList() {
    return componentList;
  }

  @Override
  public Project createNewProject(String name) {
    System.out.println("Creating new project and switching to add");
    actualProject = new Project(name,actualProject);
    componentList.add(actualProject);
    return actualProject;
  }

  @Override
  public Task createTask(String name) {
    System.out.println("creating new task with name: " + name + " and appending to the project");
    actualTask = new Task(name, actualProject);
    actualProject.add(actualTask);
    return actualTask;
  }

  @Override
  public void changeProject(String filename) {
    // NO IDEA OF WHAT IS THIS DOING
/*    int i = 0;

    do {
      if (filename == componentList[i].getName()) {
        actualProject = componentList[i];
      }else {
        i++;
      }

    }while (i < componentList.length());*/
  }

  @Override
  public boolean saveToJson() {
    try
    {
      return jsonParser.storeProjectsIntoJson(storeToJson);
    }
    catch(Exception e)
    {
      System.out.println("ERROR saving at json");
      return false;
    }
  }

  @Override
  public boolean loadFromJson(String fileName) {
    try
    {
      jsonParser.getProjectsFromJson(fileName);
    }
    catch(Exception e)
    {
      System.out.println("ERROR loading from json. Creating new empty project");
      createNewProject("default");
      return false;
    }
    return true;
  }

  @Override
  public long getTaskTime() {
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

    return time;
  }

  @Override
  public void returnToMenu() {

  }

  @Override
  public void addProjectToCurrentOne() {
    Component subproject = new Project(readFromJson, actualProject);
    actualProject.add(subproject);

  }

  @Override
  public void start() throws Exception{
    System.out.println("starting");
    System.out.println("checking for json: " + readFromJson);
    boolean exists = checkForJson();

    if (!exists) {
      System.out.println("creating new root project");
      actualProject = new Project("root", null);
    }

    else {
      System.out.println("File found..\tloading");
      loadFromJson(readFromJson);
    }

    printMenuOptions();

    Scanner sn = new Scanner(System.in);
    boolean done = false;

    while (!done) {
      try {
        System.out.println("Choose an option:");
        int option = sn.nextInt();

        switch (option) {
          case 1:
            System.out.println("You have selected option: 1");
            System.out.println("Type the project name: ");
            createNewProject(sn.nextLine());
            break;

          case 2:
            System.out.println("You have selected option 2");
            System.out.println("Type the project name where you want to create a task: ");

            // createTask(String projectName)
            createTask(sn.nextLine());
            break;

          case 3:
            System.out.println("You have selected option 3");

            // changeProject(String filename)
            break;

          case 4:
            System.out.println("You have selected option 4");
            System.out.println("Getting the time of the task: ");
            // getTaskTime()
            break;


          case 5:
            System.out.println("You have selected option 5");

            // saveToJson()
            break;


          case 6:
            System.out.println("You have selected option 6");

            //loadFromJson(String fileName)
            break;

          case 7:
            System.out.println("You have selected option 7");
            saveToJson();
            done = true;
            break;

          default:
            System.out.println("Numbers between 1-8");
        }

      } catch (InputMismatchException e) {
        System.out.println("Insert a number");
        sn.next();
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
    System.out.println("7. Exit");
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
}
