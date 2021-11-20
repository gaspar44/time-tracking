package timetracking.core;

import java.time.LocalTime;
import java.util.Observable;
import java.util.TimerTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Timer extends Observable {
  private static final int TIMER_MILLISECONDS_PERIOD = 2000;
  private static Timer instance;
  private static LocalTime date;
  private static java.util.Timer timer;
  private final Logger logger = LoggerFactory.getLogger(Timer.class);

  private Timer() {
    logger.info("creating new timer");
    timer = new java.util.Timer();
    TimerTask innerTimer = new TimerTask() {
      @Override
      public void run() {
        date = LocalTime.now();
        logger.trace("clock's time: {}", date);
        setChanged();
        logger.debug("notifying observers ");
        notifyObservers(date);
      }
    };
    timer.scheduleAtFixedRate(innerTimer, 0, TIMER_MILLISECONDS_PERIOD);
  }

  public static Timer getInstance() {
    if (instance == null) {
      instance = new Timer();
    }
    return instance;
  }

  public int getTimerMillisecondsPeriod() {
    return TIMER_MILLISECONDS_PERIOD;
  }

  public void stopCounting() {
    logger.debug("Timer stopped");
    timer.cancel();
  }

}
