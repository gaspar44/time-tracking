package timeTracking.core;

import java.util.Date;
import java.util.Observable;
import java.util.Observer;

public class TimeInterval implements Observer {
  private Date startTime;
  private Date endTime;
  private long duration;

  @Override
  public void update(Observable observable, Object o) {
      duration = duration + 1 ;
  }

  public TimeInterval() {
    startTime = new Date();
    duration = 0;
  }

  public void startCounting() {
    Timer.getInstance().addObserver(this);
  }

  public void stopCounting() {
    Timer.getInstance().deleteObserver(this);
  }

  public long getDuration() {
    return duration;
  }
}
