package timeTracking.core;

import java.util.*;

public class Timer extends Observable {
  private static Timer instance;
  private static java.util.Timer timer;
  private static TimerTask timerTask;
  private static List<Observer> timeIntervals;
  private static final int TIMER_MILISENCONDS_PERIOD = 1000;

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
        System.out.println("notifing observers...");
        notifyObservers();
      }
    };
    timer.scheduleAtFixedRate(timerTask,0,TIMER_MILISENCONDS_PERIOD);
  }


  @Override
  public void notifyObservers() {
    for (Observer ob: timeIntervals) {
      ob.update(this,null);
    }
  }

  public void addObserver(Observer ob) {
    if (ob == null) {
      return;
    }
    timeIntervals.add(ob);
  }

  public void deleteObserver(Observer ob) {
    timeIntervals.remove(ob);
  }

  public void stopCounting() {
    timer.cancel();
  }

  public List<Observer> getObservers() {
    return this.timeIntervals;
  }

}
