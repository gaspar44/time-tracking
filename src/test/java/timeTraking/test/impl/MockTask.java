package timeTraking.test.impl;

import timeTracking.core.Component;
import timeTracking.core.TimeInterval;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MockTask extends Component {
  private List<TimeInterval> timeIntervalList;
  private TimeInterval timeInterval;
  private String name;
  private long totalDuration;
  private String humanReadbleTimeDuration;

  public MockTask() {
    super(null,null);
    totalDuration = 0;
    timeIntervalList = new ArrayList<>();
  }
  public void startTime() {
    timeInterval = new TimeInterval();
    timeIntervalList.add(timeInterval);
    timeInterval.startTime();
  }

  public TimeInterval getTimeInterval() {
    return timeInterval;
  }

  public void stopTime() {
    timeInterval.stopTime();
    timeInterval = null;
  }

  public List<TimeInterval> getTimeIntervalList() {
    return timeIntervalList;
  }

  public long getTotalDuration() {
    for (TimeInterval timeInterval: timeIntervalList) {
      totalDuration = totalDuration + timeInterval.getCurrentDuration();
    }

    return totalDuration;
  }

  public String parseTimeToHumanReading() throws Exception {
    totalDuration = 0;
    getTotalDuration();
    long days = TimeUnit.SECONDS.toDays(totalDuration);
    long hours = TimeUnit.SECONDS.toHours(totalDuration) - TimeUnit.DAYS.toHours(TimeUnit.SECONDS.toDays(totalDuration));
    long minutes = TimeUnit.SECONDS.toMinutes(totalDuration) - TimeUnit.HOURS.toMinutes(TimeUnit.SECONDS.toHours(totalDuration));
    long sec = TimeUnit.SECONDS.toSeconds(totalDuration) - TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(totalDuration));
    humanReadbleTimeDuration = (String.format("%d Days %d Hours %d Minutes %d Seconds", days, hours, minutes, sec));
    return humanReadbleTimeDuration;
  }
}
