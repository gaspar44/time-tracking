package timeTracking.core;

import timeTracking.api.Visitor;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Task extends Component{
  private List<TimeInterval> timeIntervalList;
  private TimeInterval timeInterval;
  private LocalTime startTime;
  private LocalTime endTime;

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
    startTime = timeInterval.getStartTime();
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

  public void setEndTime(LocalTime endTime) {
    this.endTime = endTime;
  }

  public void setStartTime(LocalTime startTime) {
    this.startTime = startTime;
  }

  public void setTimeIntervalList(List<TimeInterval> timeIntervalList) {
    this.timeIntervalList = timeIntervalList;
  }


  public LocalTime getStartedTime() {
    return startTime;
  }

  public LocalTime getEndedTime() {
    return endTime;
  }

  @Override
  public void acceptVisitor(Visitor visitor) {
    visitor.visitTask(this);
  }
}
