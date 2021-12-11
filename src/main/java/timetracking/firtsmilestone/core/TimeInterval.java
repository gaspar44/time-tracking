package timetracking.firtsmilestone.core;

import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Observer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* "TimeInterval" notifies a "Task" ("Task" contains "Intervals)
 *  when it's been executed, for how long, and when its execution has ended through
 *  a "Timer" (see "Timer" class).
 */
public class TimeInterval implements Observer {
  private LocalDateTime startTime;
  private LocalDateTime endTime;
  private long duration;
  private final Task fatherTask;
  private final Logger logger = LoggerFactory.getLogger(TimeInterval.class);


  public TimeInterval(Task task) {
    assert (task != null);  //Precondition
    fatherTask = task;
    duration = 0;
  }

  @Override
  public void update(Observable observable, Object obj) {
    duration = duration + Timer.getInstance().getTimerMillisecondsPeriod() / 1000;
    logger.debug("updating time interval time");
    logger.trace("received value {}", endTime);

    endTime = (LocalDateTime) obj;
    assert (endTime != null);

    logger.debug("updating father's time");
    fatherTask.setEndTime(endTime);

    logger.debug("updating total time of father");
    fatherTask.addTimeDuration(Timer.getInstance().getTimerMillisecondsPeriod() / 1000);
  }

  public void startTime() {
    startTime = LocalDateTime.now();
    logger.debug("subscribing to clock");
    logger.trace("Adding observer: {}", fatherTask.getName());
    Timer.getInstance().addObserver(this);
  }

  public void stopTime() {
    logger.debug("unsubscribing to clock");
    logger.trace("unsubscribing {} of clock", fatherTask.getName());
    Timer.getInstance().deleteObserver(this);
  }

  public LocalDateTime getEndTime() {
    return endTime;
  }

  public void setEndTime(LocalDateTime endTime) {
    logger.debug("setting endTime");
    assert (endTime != null);
    this.endTime = endTime;
  }

  public LocalDateTime getStartTime() {
    return startTime;
  }

  public void setStartTime(LocalDateTime startTime) {
    logger.debug("setting new start time");
    assert (startTime != null);
    this.startTime = startTime;
  }

  public long getCurrentDuration() {
    return duration;
  }

  public void setDuration(long duration) {
    assert (duration >= 0); // Precondition
    this.duration = duration;
  }
}
