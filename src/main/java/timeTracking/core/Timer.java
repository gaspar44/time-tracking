package timeTracking.core;

import java.util.*;

public class Timer extends Observable {
  private static Timer instance;
  private static java.util.Timer timer;
  private static TimerTask timerTask;
  private static List<Observer> timeIntervals;
  private static long passedTime = 0;

  public static Timer getInstance() {
    if (instance == null) {
      instance = new Timer();
    }
    return instance;
  }

  public Timer() {
    timeIntervals = new ArrayList<>();
    timer = new java.util.Timer();
    timerTask = new TimerTask() {
      @Override
      public void run() {
        synchronized(this) {
          passedTime++;
        }
      }
    };

  }

  public synchronized void addObserver(Observer ob) {
    if (ob == null) {
      return;
    }
    timeIntervals.add(ob);
  }

  public synchronized void deleteObserver(Observer ob) {
    timeIntervals.remove(ob);
  }

  public void startCounting() {
    timer.scheduleAtFixedRate(timerTask,0,1000);
  }

  public void stopCounting() {
    timer.cancel();
  }

  public long getPassedTime() {
    return passedTime;
  }
}
