package timetracking.core;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import timetracking.api.Visitor;

public class Task extends Component {
  private List<TimeInterval> timeIntervalList;
  private TimeInterval timeInterval;
  private final Logger logger = LoggerFactory.getLogger(Task.class);

  public Task(String name, Project father, String... tags) {
    super(name, father, tags);
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
    this.timeIntervalList = timeIntervalList;
    if (timeIntervalList.size() != 0) {
      startTime = timeIntervalList.get(0).getStartTime();
      endTime = timeIntervalList.get(timeIntervalList.size() - 1).getEndTime();
    }
  }

  public TimeInterval getTimeInterval() {
    return timeInterval;
  }

  public TimeInterval startNewInterval() {
    logger.info("starting new time interval for task {}", this.getName());
    timeInterval = new TimeInterval(this);
    timeIntervalList.add(timeInterval);
    timeInterval.startTime();
    setStartTime(timeIntervalList.get(0).getStartTime());

    return timeInterval;
  }

  public TimeInterval stopActualInterval() {
    logger.info("stopping actual time interval for task {}", this.getName());

    if (timeInterval != null) {
      timeInterval.stopTime();
    }

    TimeInterval ret = timeInterval;
    timeInterval = null;
    return ret;
  }

  @Override
  public void acceptVisitor(Visitor visitor) {
    visitor.visitTask(this);
  }
}
