package timetracking.firtsmilestone.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import timetracking.firtsmilestone.core.IdGenerator;
import timetracking.firtsmilestone.core.Project;
import timetracking.firtsmilestone.core.Task;

public class DemoTree {
  private final Project rootProject;
  private final Project softwareDesign;
  private final Project softwareTesting;
  private final Project dataBase;
  private final Task transPortation;
  private final Project problems;
  private final Project projectTimeTracke;

  private final Task firtslist;
  private final Task secondList;
  private final Task readHandle;
  private final Task firstMilestone;
  private final Logger logger = LoggerFactory.getLogger(DemoTree.class);

  public DemoTree() {
    logger.info("Creating new Demo tree");
    IdGenerator.getInstance().reset();
    rootProject = new Project("root", null);
    softwareDesign = new Project("software design", rootProject, "java", "flutter");
    softwareTesting = new Project("software testing", rootProject, "c++", "Java", "python");
    dataBase = new Project("dataBasse", rootProject, "SQL", "python", "C++");
    transPortation = new Task("task transportation", rootProject);

    problems = new Project("problems", softwareDesign);
    projectTimeTracke = new Project("time tracker", softwareDesign);

    firtslist = new Task("firts list", problems, "java");
    secondList = new Task("Second list", problems, "Dart");
    readHandle = new Task("read handle", projectTimeTracke);

    firstMilestone = new Task("firstMilestone", projectTimeTracke, "Java", "IntelliJ");
  }

  public Project getRootProject() {
    return rootProject;
  }

  public Project getSoftwareDesign() {
    return softwareDesign;
  }

  public Project getSoftwareTesting() {
    return softwareTesting;
  }

  public Project getDataBase() {
    return dataBase;
  }

  public Task getTransPortation() {
    return transPortation;
  }

  public Project getProblems() {
    return problems;
  }

  public Project getProjectTimeTracke() {
    return projectTimeTracke;
  }

  public Task getFirtslist() {
    return firtslist;
  }

  public Task getSecondList() {
    return secondList;
  }

  public Task getReadHandle() {
    return readHandle;
  }

  public Task getFirstMilestone() {
    return firstMilestone;
  }
}
