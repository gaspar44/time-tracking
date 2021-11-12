package timetracking.core;

import java.time.LocalTime;
import java.util.Observable;
import java.util.TimerTask;

public class Timer extends Observable {
  private static final int TIMER_MILLISECONDS_PERIOD = 2000;
  private static Timer instance;
  private static LocalTime date;
  private static java.util.Timer timer;
  private static TimerTask innerTimer;

  private Timer() {
    timer = new java.util.Timer();
    innerTimer = new TimerTask() {
      @Override
      public void run() {
        date = LocalTime.now();
        setChanged();
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

  public LocalTime getDate() {
    return date;
  }


  public void stopCounting() {
    timer.cancel();
  }

}
