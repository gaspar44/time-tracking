package timeTracking.core;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Task extends Component{
  private List<TimeInterval> timeIntervalList;
  private TimeInterval timeInterval;
  private int countedTimeIntervalsDuration;

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
    timeInterval = new TimeInterval();
    timeIntervalList.add(timeInterval);
    timeInterval.startTime();
    return timeInterval;
  }

  public TimeInterval stopActualInterval() {
    TimeInterval ret = timeInterval;
    timeInterval.stopTime();
    getTotalTime();

    timeInterval = null;
    return ret;
  }

  public LocalTime getStartedTime() {
    return timeInterval == null ? timeIntervalList.get(timeIntervalList.size() - 1 ).getStartTime() : timeInterval.getStartTime();
  }

  public LocalTime getEndedTime() {
    return timeInterval == null ? timeIntervalList.get(timeIntervalList.size() - 1 ).getEndTime() : timeInterval.getEndTime();
  }

  @Override
  public void accept(JsonParser visitor) {

  }



  @Override
  public long getTotalTime() {
    long differentTimeToAdd = 0;

    for (int i = countedTimeIntervalsDuration; i < timeIntervalList.size() ; i++) {
      differentTimeToAdd = differentTimeToAdd + timeIntervalList.get(i).getCurrentDuration();
    }

    countedTimeIntervalsDuration = timeIntervalList.size();

    addTimeDuration(differentTimeToAdd);
    father.addTimeDuration(differentTimeToAdd);
    return totalTime;
  }
}
