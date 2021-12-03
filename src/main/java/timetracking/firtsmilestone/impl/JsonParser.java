package timetracking.firtsmilestone.impl;

import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import timetracking.firtsmilestone.api.Visitor;
import timetracking.firtsmilestone.core.Component;
import timetracking.firtsmilestone.core.Project;
import timetracking.firtsmilestone.core.Task;
import timetracking.firtsmilestone.core.TimeInterval;

/* This class creates a JSON type of file which contains
 * the Component list tree (see "Project" for further information),
 * that it's a collection of components which are related to each
 * other in a "Project-Project" way or "Project-Task".
*/
public class JsonParser implements Visitor {
  // JSON's Keys:


  private static JsonParser instance;
  private String fileName;
  private JSONArray projectTree;
  private JSONObject rootJsonProject;
  private Project parsedTreeFromFile;
  private final Logger logger = LoggerFactory.getLogger(JsonParser.class);

  private JsonParser() {
    logger.info("starting Json Parser");
    projectTree = new JSONArray();
    rootJsonProject = new JSONObject();
  }

  public static JsonParser getInstance() {
    if (instance == null) {
      instance = new JsonParser();
    }
    return instance;
  }

  public Project getProjectsFromJson(String fileName) throws Exception {
    logger.info("getting project from json");
    String stringProjectTree = Files.readString(Paths.get(fileName));

    // tokener let the array to transform the string into a JSONArrayObject.
    JSONTokener tokener = new JSONTokener(stringProjectTree);
    JSONArray jsonArrayProjectTree = new JSONArray(tokener);
    logger.debug("readed, starting transformation into components");

    for (int i = 0; i < jsonArrayProjectTree.length(); i++) {
      transformJsonArrayIntoProject(jsonArrayProjectTree, null);
    }

    return parsedTreeFromFile;
  }

  private void transformJsonArrayIntoProject(JSONArray jsonArrayProjectTree, Component father) {
    for (int i = 0; i < jsonArrayProjectTree.length(); i++) {
      JSONObject unparsedJsonObject = (JSONObject) jsonArrayProjectTree.get(i);

      if (unparsedJsonObject.get(JsonKeys.TYPE_KEY).equals(JsonKeys.PROJECT_TYPE)) {
        logger.debug("Parsing as Project");
        parseJsonElementAsProject(unparsedJsonObject, father);
      } else if (unparsedJsonObject.get(JsonKeys.TYPE_KEY).equals(JsonKeys.TASK_TYPE)) {
        logger.debug("Parsing as task");
        parseJsonElementAsTask(unparsedJsonObject, father);
      }
    }
  }

  private void parseJsonElementAsTask(JSONObject unparsedJsonObject, Component father) {
    assert (father != null);  // Precondition
    Task task;
    String taskName = unparsedJsonObject.getString(JsonKeys.NAME_KEY);
    task = new Task(taskName, (Project) father);
    task.setTotalTime(unparsedJsonObject.getLong(JsonKeys.DURATION_KEY));
    JSONArray jsonArrayTimeIntervalList;

    try {
      jsonArrayTimeIntervalList = unparsedJsonObject.getJSONArray(JsonKeys.TIME_INTERVAL_KEY);
    } catch (Exception e) {
      return;
    }

    List<TimeInterval> timeIntervalList = new ArrayList<>();
    for (int i = 0; i < jsonArrayTimeIntervalList.length(); i++) {

      JSONObject jsonTimeInterval = (JSONObject) jsonArrayTimeIntervalList.get(i);
      String startTime = jsonTimeInterval.getString(JsonKeys.START_TIME_KEY);
      String endTime = jsonTimeInterval.getString(JsonKeys.END_TIME_KEY);
      long currentDuration = jsonTimeInterval.getLong(JsonKeys.CURRENT_TIME_INTERVAL_DURATION);

      TimeInterval timeInterval = new TimeInterval(task);

      timeInterval.setDuration(currentDuration);
      timeInterval.setStartTime(LocalTime.parse(startTime));
      timeInterval.setEndTime(LocalTime.parse(endTime));
      timeIntervalList.add(timeInterval);
    }

    task.setTimeIntervalList(timeIntervalList);
  }

  private void parseJsonElementAsProject(JSONObject unparsedObject, Component father) {
    Project project;
    String projectName = unparsedObject.getString(JsonKeys.NAME_KEY);
    project = new Project(projectName, (Project) father);

    project.setTotalTime(unparsedObject.getLong(JsonKeys.DURATION_KEY));
    try {
      String startTime = unparsedObject.getString(JsonKeys.START_TIME_KEY);
      String endTime = unparsedObject.getString(JsonKeys.END_TIME_KEY);
      project.setStartTime(LocalTime.parse(startTime));
      project.setEndTime(LocalTime.parse(endTime));
    } catch (Exception e) {
      logger.warn("Project without started task {} ", projectName);
    }

    JSONArray components;
    List<String> tags = new ArrayList<>();

    try {
      components = unparsedObject.getJSONArray(JsonKeys.COMPONENT_KEY);
      JSONArray unparsedTags = unparsedObject.getJSONArray(JsonKeys.TAGS_KEY);

      for (int i = 0; i < unparsedTags.length(); i++) {
        tags.add(unparsedTags.getString(i));
      }

    } catch (Exception e) {
      logger.warn(e.getMessage());
      return;
    }

    if (father == null) {
      parsedTreeFromFile = project;
    }
    transformJsonArrayIntoProject(components, project);
  }

  private boolean storeProjectsIntoJson(String storeToJson, Project project) {
    /*This must be done because we want to write from the root until the end
    we don't want to write from some nodes, we want from the start.
    This grants a way to end the recursion.
    */
    if (project.getFather() != null) {
      return true;
    }

    try {
      if (!projectTree.isEmpty()) {
        FileWriter writer = new FileWriter(storeToJson, false);
        writer.write(projectTree.toString(4));

        writer.flush(); //We make sure that Project Tree is correctly written to the Json file.
        writer.close();
        logger.debug("json saved");
      } else {
        logger.warn("no json project to store found");
        return false;
      }
    } catch (Exception e) {
      logger.warn(e.getMessage());
      return false;
    }

    /*This is done to reset the actual project. If this is not made,
     the project will be added as a subproject of itself.
    */
    logger.debug("resetting json trees for future uses");
    rootJsonProject = new JSONObject();
    projectTree = new JSONArray();

    return true;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  @Override
  public void visitTask(Task task) {
    JSONObject jsonObject = task.toJson();
    JSONArray components = rootJsonProject.getJSONArray(JsonKeys.COMPONENT_KEY);
    components.put(jsonObject);
  }

  @Override
  public void visitProject(Project project) {
    final List<Component> components = project.getComponents();
    JSONObject jsonObject = project.toJson();

    if (project.getFather() != null) {
      JSONArray fatherJsonArray = rootJsonProject.getJSONArray(JsonKeys.COMPONENT_KEY);
      fatherJsonArray.put(jsonObject);
    } else {
      projectTree.put(jsonObject);
    }
    /*
    This is done because we are going thought recursive iteration
    if we don't set a previous object, when we return to this scope ,
    will lose the previous state, this causes that some projects
    can be converted into child of their brothers.
    This behaviour is unexpected
    */

    JSONObject previous = rootJsonProject;
    rootJsonProject = jsonObject;

    for (Component component : components) {
      component.acceptVisitor(this);
    }

    rootJsonProject = previous;
    storeProjectsIntoJson(fileName, project);
  }
}
