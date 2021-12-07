package timetracking.firtsmilestone.core;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import timetracking.firtsmilestone.api.Visitor;

/*
 * This class can be a "Project" or "Task", which contains
 * all the information about each of them, depending on what
 * kind of component is (start/end time, tags, name...) and
 * its Component "father". It also shows his information in
 * a humanly readable way.
 *
 * Note: The first "Project" of the whole Tree of components
 *       is the father we've talked about before.
*/

public abstract class Component {
  protected Component father;
  protected long totalTime;
  protected LocalTime startTime;
  protected LocalTime endTime;
  protected List<String> tags;
  protected int id;
  private final String name;
  private final Logger logger = LoggerFactory.getLogger(Component.class);

  public Component(String componentName, Component father, String... tags) {
    this(componentName, father);
    this.tags = Arrays.asList(tags);
    logger.info("initialize component");
    logger.trace("component name: {}, father name: {}, tags :{}",
        componentName, father.getName(), tags);
  }

  public Component(String componentName, Component father) {
    logger.info("initialize component");
    this.name = componentName;
    this.father = father;
    this.totalTime = 0;
    if (father != null) {
      logger.trace("component name: {}, father name: {}, tags :{}",
          componentName, father.getName(), tags);
    }

    logger.trace("Generating ID: {}", IdGenerator.getInstance().getNextId());
    this.id = IdGenerator.getInstance().generateId();
    this.tags = new ArrayList<>();
  }

  public String getName() {
    return name;
  }

  public Component getFather() {
    return father;
  }

  public long getTotalTime() {
    assert (totalTime >= 0);  // Precondition
    logger.debug("getting time");
    logger.trace("total time is: {}", totalTime);
    return totalTime;
  }

  public void setTotalTime(long totalTime) {
    assert (totalTime >= 0);  // Precondition
    logger.debug("setting time");
    logger.trace("total time is: {}", totalTime);
    this.totalTime = totalTime;
  }

  public LocalTime getStartedTime() {
    return startTime;
  }

  public LocalTime getEndedTime() {
    return endTime;
  }

  public void setStartTime(LocalTime startTime) {
    this.startTime = startTime;
    logger.debug("set start time to component: {}", name);
    logger.trace("time is: {}", startTime);
    if (father != null && father.getStartedTime() == null) {
      logger.debug("setting start time to {} father of {}", father.getName(), name);
      father.setStartTime(startTime);
    }
  }

  public void setEndTime(LocalTime endTime) {
    this.endTime = endTime;
    logger.debug("set end time to component: {}", name);
    logger.trace("time is: {}", endTime);
    if (father != null) {
      logger.debug("setting end time to {} father of {}", father.getName(), name);
      father.setEndTime(endTime);
    }
  }

  public void setTags(List<String> tags) {
    this.tags = tags;
  }

  public void addTag(String tag) {
    this.tags.add(tag);
  }

  public int getId() {
    return id;
  }

  public List<String> getTags() {
    return this.tags;
  }

  public abstract void acceptVisitor(Visitor visitor);

  private void printTime() {
    logger.info("Name: {}\tInitial date: {}\tFinal date: {}\tDuration : {}",
        this.name,
        this.startTime,
        this.endTime,
        this.totalTime);
  }

  protected void addTimeDuration(long moreDuration) {
    assert (moreDuration >= 0); //Precondition
    totalTime = totalTime + moreDuration;
    logger.debug("adding time to component {}", this.name);

    printTime();
    if (father != null) {
      logger.debug("adding time to component {} father of {}", father.getName(), this.name);
      father.addTimeDuration(moreDuration);
    }
  }

  public abstract JSONObject toJson();

  public abstract JSONObject toJson(int treeDeep);

  public abstract Component findComponentById(int id);
}