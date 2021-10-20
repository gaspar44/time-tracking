package timeTracking.core;

import java.util.Date;
import java.util.List;

public class Task extends Component{
  private List<TimeInterval> timeIntervalList;
  private TimeInterval timeInterval;
  private String nametask;
  private long totalDuration;
  private String humanReadbleTimeDuration;
  private String TaskID;

  public Task(String name, Project father, String IDTask) {
    this.nametask=name;
    this.TaskID = IDTask;


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
    return nametask;
  }

  @Override
  public String getID() {
    return TaskID;
  }

  @Override
  public void accept(JsonParser visitor) {

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
