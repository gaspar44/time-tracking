package timeTracking.core;

import java.util.Date;
import java.util.List;

public class Task extends Component{
  private List<TimeInterval> timeIntervalList;
  private TimeInterval timeInterval;
  private String name;
  private long totalDuration;
  private String humanReadbleTimeDuration;

  public Task(String name, Project father) {
    super(name,father);
    totalDuration = 0;
  }

  public TimeInterval getTimeInterval() {
    return timeInterval;
  }

  public Date getStartedTime() {
   return timeInterval.getStartTime();
  }

  public Date getEndedTime() {
    return timeInterval.getEndTime();
  }

  public String getName() {
    return name;
  }

  public long getTotalDuration() {
    return totalDuration;
  }

  public String getHumanReadbleTimeDuration() {
    return humanReadbleTimeDuration;
  }

  @Override
  public long getTotalTime() {
    return 0;
  }
}
