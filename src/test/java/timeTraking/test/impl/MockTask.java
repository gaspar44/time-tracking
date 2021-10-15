package timeTraking.test.impl;

import timeTracking.core.Component;
import timeTracking.core.TimeInterval;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MockTask extends Component {
  private List<TimeInterval> timeIntervalList;
  private TimeInterval timeInterval;
  private String name;
  private long totalDuration;
  private String humanReadbleTimeDuration;

  public MockTask() {
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
    getTotalDuration();
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

  public Date parseTimeToHumanReading() throws ParseException {
    SimpleDateFormat formarter = new SimpleDateFormat("dd-MM-yyyy");
    TimeInterval firstTime = timeIntervalList.get(0);
    Date startedTime = firstTime.getStartTime();
    Instant start = Instant.parse(startedTime.toString());

    return formarter.parse(String.valueOf(3));
  }
}
