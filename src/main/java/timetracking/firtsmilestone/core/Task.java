package timetracking.firtsmilestone.core;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import timetracking.firtsmilestone.api.Visitor;
import timetracking.firtsmilestone.impl.JsonKeys;

/* This class is based on a kind of Component called "Task",
 * which HAS to be contained in a "Project", another kind of
 * Component (explained in "Project" class). Apart from getting
 * what a "Task" should have (its father and name), it also gets
 * the list of "Intervals" of the time it's been executed and
 * shows it to us.
*/

public class Task extends Component {
  private List<TimeInterval> timeIntervalList;
  private TimeInterval timeInterval;
  private boolean active;
  private final Logger logger = LoggerFactory.getLogger(Task.class);

  public Task(String name, Project father, String... tags) {
    super(name, father, tags);
    this.active = false;
    logger.trace("Project {} created", name);
    init(father);
  }

  public Task(String name, Project father) {
    super(name, father);
    init(father);
  }

  private void init(Project father) {
    logger.info("creating task");
    timeIntervalList = new ArrayList<>();
    father.add(this);
  }

  public List<TimeInterval> getTimeIntervalList() {
    return timeIntervalList;
  }

  public void setTimeIntervalList(List<TimeInterval> timeIntervalList) {
    assert (timeIntervalList != null);  //Precondition
    this.timeIntervalList = timeIntervalList;
    if (timeIntervalList.size() != 0) {
      startTime = timeIntervalList.get(0).getStartTime();
      endTime = timeIntervalList.get(timeIntervalList.size() - 1).getEndTime();
    }
  }

  public TimeInterval getTimeInterval() {
    return timeInterval;
  }

  public boolean isActive() {
    return this.active;
  }

  public TimeInterval startNewInterval() {
    final int size = timeIntervalList.size();
    logger.info("starting new time interval for task {}", this.getName());
    timeInterval = new TimeInterval(this);
    this.active = true;
    timeIntervalList.add(timeInterval);
    assert (timeIntervalList.size() == size + 1);
    timeInterval.startTime();
    setStartTime(timeIntervalList.get(0).getStartTime());

    return timeInterval;
  }

  public TimeInterval stopActualInterval() {
    logger.info("stopping actual time interval for task {}", this.getName());
    if (timeInterval != null) {
      timeInterval.stopTime();
    }
    this.active = false;
    TimeInterval ret = timeInterval;
    timeInterval = null;
    return ret;
  }

  @Override
  public void acceptVisitor(Visitor visitor) {
    visitor.visitTask(this);
  }

  @Override
  public JSONObject toJson() {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put(JsonKeys.NAME_KEY, this.getName());
    jsonObject.put(JsonKeys.FATHER_NAME, this.getFather().getName());
    jsonObject.put(JsonKeys.TYPE_KEY, "Task");
    jsonObject.put(JsonKeys.DURATION_KEY, this.getTotalTime());
    jsonObject.put(JsonKeys.TAGS_KEY, this.getTags());
    jsonObject.put(JsonKeys.ID_KEY, this.getId());
    jsonObject.put(JsonKeys.ACTIVE_KEY, this.isActive());
    JSONArray timeIntervals = new JSONArray();

    List<TimeInterval> timeIntervalList = this.getTimeIntervalList();

    for (TimeInterval timeInterval : timeIntervalList) {
      JSONObject jsonTimeInterval = new JSONObject();
      jsonTimeInterval.put(JsonKeys.START_TIME_KEY, timeInterval.getStartTime());
      jsonTimeInterval.put(JsonKeys.END_TIME_KEY, timeInterval.getEndTime());
      jsonTimeInterval.put(JsonKeys.CURRENT_TIME_INTERVAL_DURATION,
          timeInterval.getCurrentDuration());
      timeIntervals.put(jsonTimeInterval);
    }

    jsonObject.put(JsonKeys.TIME_INTERVAL_KEY, timeIntervals);
    return jsonObject;
  }

  @Override
  public JSONObject toJson(int treeDeep) {
    return toJson();
  }

  @Override
  public Component findComponentById(int id) {
    if (id == this.id) {
      return this;
    }

    return null;
  }
}
