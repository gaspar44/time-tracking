package timeTracking.core;

import java.time.LocalTime;
import java.util.Observable;
import java.util.Observer;

public class TimeInterval implements Observer {
  private LocalTime startTime;
  private LocalTime endTime;
  private long duration;
  private Task fatherTask;

  @Override
  public void update(Observable observable, Object obj) {
      duration = duration + Timer.getInstance().getTimerMillisecondsPeriod() / 1000 ;
      endTime = (LocalTime) obj;
      fatherTask.addTimeDuration(Timer.getInstance().getTimerMillisecondsPeriod() / 1000);

  }

  public TimeInterval(Task task) {
    fatherTask = task;
    duration = 0;
  }

  public void startTime() {
    startTime = LocalTime.now();
    System.out.println("Adding observer");
    Timer.getInstance().addObserver(this);
  }

  public void stopTime() {
    System.out.println("Deleting Observer");
    Timer.getInstance().deleteObserver(this);
  }

  public LocalTime getEndTime(){
    return endTime;
  }

  public LocalTime getStartTime() {
    return startTime;
  }

  public long getCurrentDuration() {
    return duration;
  }

  public void setDuration(long duration) {
    this.duration = duration;
  }

  public void setStartTime(LocalTime startTime) {
    this.startTime = startTime;
  }

  public void setEndTime(LocalTime endTime) {
    this.endTime = endTime;
  }
}
