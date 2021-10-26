package timeTracking.core;

import timeTracking.api.Visitor;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Task extends Component{
  private List<TimeInterval> timeIntervalList;
  private TimeInterval timeInterval;

  public Task(String name, Project father) {
    super(name,father);
    timeIntervalList = new ArrayList<>();
    father.add(this);
  }

  public List<TimeInterval> getTimeIntervalList(){
    return timeIntervalList;
  }

  public TimeInterval getTimeInterval() {
    return timeInterval;
  }

  public TimeInterval startNewInterval() {
    timeInterval = new TimeInterval(this);
    timeIntervalList.add(timeInterval);
    timeInterval.startTime();
    return timeInterval;
  }

  public TimeInterval stopActualInterval() {
    TimeInterval ret = timeInterval;

    if (timeInterval != null ) {
      timeInterval.stopTime();
    }


    timeInterval = null;
    return ret;
  }

  public void setTimeInterval(TimeInterval timeInterval) {
    this.timeInterval = timeInterval;
  }

  public void setTimeIntervalList(List<TimeInterval> timeIntervalList) {
    this.timeIntervalList = timeIntervalList;
  }


  public LocalTime getStartedTime() {
    return timeInterval == null ? timeIntervalList.get(timeIntervalList.size() - 1 ).getStartTime() : timeInterval.getStartTime();
  }

  public LocalTime getEndedTime() {
    return timeInterval == null ? timeIntervalList.get(timeIntervalList.size() - 1 ).getEndTime() : timeInterval.getEndTime();
  }

  @Override
  public void acceptVisitor(Visitor visitor) {
    visitor.visitTask(this);
  }
}
