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
    System.out.println("in fita 2");
  }

  @Override
  public boolean saveToJson(String fileName) {
    try
    {
      jsonParser.setFileName(fileName);
      rootProject.acceptVisitor(jsonParser);
      return true;
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
      rootProject = jsonParser.getProjectsFromJson(fileName);
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
    printMenuOptions();

    Scanner sn = new Scanner(System.in);
    boolean done = false;

    while (!done) {
      try {
        printMenuOptions();
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
            System.out.print("Please, insert the name of the JSON file: ");
            sn.nextLine();
            String option5 = sn.nextLine();
            actualTask.stopActualInterval();
            boolean success = saveToJson(option5);

            if (success) {
              System.out.println("You have saved the jSON file with the name " + option5);
            }

            break;

          case 4:
            System.out.println("You have selected option 4");
            System.out.println("Getting the time of the task: ");
            System.out.print("Please, insert the name of the JSON file: ");
            sn.nextLine();
            String option4 = sn.nextLine();
            boolean exists = checkForJson(option4);

            if (!exists) {
              System.out.print("JSON not found");
              break;
            }

            boolean readed = loadFromJson(option4);

            if (readed) {
              System.out.println("You have saved the jSON file with the name " + option4);
            }

            break;
            
          case 5:
            System.out.println("task: " + actualTask.getName() + " time: " + getTaskTime());
            break;

          case 6:
            System.out.println("You have selected option 6");
            System.out.println("Project: " + actualProject.getName() + " time: " + actualProject.getHumanReadableTimeDuration());
            break;

            case 7:
            System.out.println("You have selected option 7");
            if (actualTask == null ) {
              System.out.println("To time of a task, a task must be created");
              break;
            }

            System.out.println("Starting " + actualTask.getName());
            actualTask.startNewInterval();
            break;

          case 8:
            System.out.println("You have selected option 9");
            if (actualTask == null) {
              System.out.println("to stop a task, a task must be created");
              break;
            }

            actualTask.stopActualInterval();

          case 9:
            System.out.println("You have selected option 9");
            done = true;
            break;

          default:
            System.out.println("Numbers between 1-9");
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
    System.out.println("7. Start task time");
    System.out.println("8. Stop task time");
    System.out.println("9. Exit");
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
