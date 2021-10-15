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

  public void startTime() {
    System.out.println("Adding observer");
    Timer.getInstance().addObserver(this);
  }

  public void stopTime() {
    System.out.println("Deleting Observer");
    Timer.getInstance().deleteObserver(this);
    calculateEndTime();
  }

  private void calculateEndTime() {
    // duration is at sec and getTime() is a mSec
    endTime = new Date(startTime.getTime() + duration * 1000);
  }

  public Date getEndTime(){
    return endTime;
  }

  public Date getStartTime() {
    return startTime;
  }

  public long getCurrentDuration() {
    return duration;
  }
}
