package timeTracking.impl;

import timeTracking.api.MenuInterface;
import timeTracking.core.Project;
import timeTracking.core.Task;

import java.io.File;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleMenu implements MenuInterface {
  private Project rootProject;
  private Project actualProject;
  private Task actualTask;
  private static JsonParser jsonParser;

  public ConsoleMenu() {
    jsonParser = JsonParser.getInstance();
    System.out.println("creating new root project");
    rootProject = new Project("root", null);
    actualProject = null;
  }

  @Override
  public Project getRootProject() {
    return rootProject;
  }

  @Override
  public Project createNewProject(String name) {
    System.out.println("Creating new project and switching to add");
    actualProject = actualProject == null ? new Project(name,rootProject) : new Project(name,actualProject);
    return actualProject;
  }

  @Override
  public Task createTask(String name) {
    System.out.println("creating new task with name: " + name + " and appending to the project");
    actualTask = new Task(name, actualProject);
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
  public boolean saveToJson(String fileName) {
    try
    {
      return jsonParser.storeProjectsIntoJson(fileName);
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
    System.out.println(actualTask.getHumanReadableTimeDuration());
    return actualTask.getTotalTime();
  }

  @Override
  public void returnToMenu() {

  }

  @Override
  public void addProjectToCurrentOne() {

  }

  @Override
  public void start() throws Exception{
    System.out.println("starting");
    // Needs to be updated:
/*    boolean exists = checkForJson();

    if (exists) {
      System.out.println("File found..\tloading");
      loadFromJson();
    }*/

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
            System.out.print("Type the project name: ");
            sn.nextLine();
            String option1 = sn.nextLine();
            createNewProject(option1);


            break;

          case 2:
            System.out.println("You have selected option 2");
            System.out.print("Type the project name where you want to create a task: ");
            sn.nextLine();
            String option2 = sn.nextLine();
            createTask(option2);;
            break;

          case 3:
            System.out.println("You have selected option 3");

            // changeProject(String filename)
            break;

          case 4:
            System.out.println("You have selected option 4");
            System.out.println("Getting the time of the task: ");
            getTaskTime();
            break;


          case 5:
            System.out.println("You have selected option 5");
            System.out.print("Please, insert the name of the JSON file: ");
            sn.nextLine();
            String option5 = sn.nextLine();
            saveToJson(option5);
            System.out.println("You have saved the jSON file with the name " + option5);

            break;

          case 6:
            System.out.println("You have selected option 6");
            System.out.println("Insert the name of the JSON file you want to load: ");
            sn.nextLine();
            String option5r = sn.nextLine();
            saveToJson(option5r);
            sn.nextLine();
            String option6 = sn.nextLine();
            if(option6 == option5r)
            {
                loadFromJson(option5r);
            }
            else
              System.out.println("Error: This JSON file doesn't exist, please try again");
            //loadFromJson(String fileName)
            break;

          case 7:
            System.out.println("You have selected option 7");

            saveToJson(sn.nextLine());
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


  private boolean checkForJson(String readFromJson) throws Exception {
    File jsonToReadFrom = new File(readFromJson);
    if (!jsonToReadFrom.exists()) {
      System.out.println("Json does not exists, a new one with name: " + readFromJson + " will be created");
      jsonToReadFrom.createNewFile();
      return false;
    }
    return true;
  }
}
