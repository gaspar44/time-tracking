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

  public ConsoleMenu(String readFromJson, String storeToJson) {
    this.readFromJson = readFromJson;
    this.storeToJson = storeToJson;
    componentList = new ArrayList<>();
  }

  public ConsoleMenu(String readFromJson) {
    new ConsoleMenu(readFromJson,readFromJson);
  }


  @Override
  public List<Component> getComponentList() {
    return componentList;
  }

 /* @Override
  public void changeProject(Project newProject) {

  }*/


  @Override
  public Project createNewProject(String name) {
    System.out.println("Creating new project and swiching to add");
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

  public void changeProject(String filename) {

    int i = 0;

    do {
      if (filename == componentList[i].getName()) {
        actualProject = componentList[i];
      }else {
        i++;
      }

    }while (i < componentList.length());
  }

  @Override
  public boolean saveToJson() {
    boolean correcte = false;
    try
    {
      JsonParser saveJson = new JsonParser(todo viene del component);
      saveJson.storeProjectsIntoJson(storeToJson);
      correcte = true;
    }
    catch(Exception e)
    {
      System.out.println("No s'ha pogut guardar l'arxiu JSON");
    }
    return correcte;
  }

  @Override
  public void loadFromJson(String fileName) {
    try
    {
      JsonParser loadJson = new JsonParser(todo viene del component);
      loadJson.getProjectsFromJson(fileName);
    }
    catch(Exception e)
    {
      System.out.println("No s'ha pogut carregar l'arxiu JSON");
    }
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

  public void returnToMenu() {

  }

  @Override
  public void addProjectToCurrentOne() {
    Component subproject = new Project(readFromJson, actualProject);
    actualProject.add(subproject);

  }


  public static boolean quit() {
    return true;
  }

  @Override
  public void start() throws Exception{
    System.out.println("starting");
    System.out.println("checking for json: " + readFromJson);
    boolean exists = checkForJson();

    if (!exists) {
      System.out.println("creating new empty project");
      Project actualProject = new Project("root", null);
    }

    Scanner sn = new Scanner(System.in);
    boolean salir = false;

    while (!salir) {

      System.out.println("1. Create new project.");
      System.out.println("2. Create new task");
      System.out.println("3. Change project");
      System.out.println("4. Get task time");
      System.out.println("5. Save project to JSON");
      System.out.println("6. Load project from JSON");
      System.out.println("7. Exit"); //Done


      try {

        System.out.println("Choose an option:");
        int opcion = sn.nextInt();

        switch (opcion) {
          case 1:
            System.out.println("You have selected option: 1");
            System.out.println("Type the project name: ");

            // createNewProject(String projectName)
            createNewProject(sn.nextLine());
            break;

          case 2:
            System.out.println("Has seleccionado la opcion 2");
            System.out.println("Type the project name where you want to create a task: ");

            // createTask(String projectName)
            createTask(sn.nextLine());
            break;

          case 3:
            System.out.println("Has seleccionado la opcion 3");

            // changeProject(String filename)
            break;

          case 4:
            System.out.println("Has seleccionado la opcion 4");
            System.out.println("Getting the time of the task: ");
            // getTaskTime()
            break;


          case 5:
            System.out.println("Has seleccionado la opcion 5");

            // saveToJson()
            break;


          case 6:
            System.out.println("Has seleccionado la opcion 6");

            //loadFromJson(String fileName)
            break;

          case 7:

            System.out.println("Has seleccionado la opcion 7");
            salir = quit();
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

  @Override
  public void exit() {

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
