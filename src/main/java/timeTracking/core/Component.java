package timeTracking.core;

import timeTracking.api.Visitor;

import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

public abstract class Component {
  private String name;
  protected Component father;
  protected long totalTime;
  protected LocalTime startTime;
  protected LocalTime endTime;

  public Component(String componentName,Component father)
  {
    this.name = componentName;
    this.father = father;
    this.totalTime = 0;
  }

  public String getName() {
    return name;
  }

  private String getHumanReadableTimeDuration(long totalDuration) {
    long days = TimeUnit.SECONDS.toDays(totalDuration);
    long hours = TimeUnit.SECONDS.toHours(totalDuration) - TimeUnit.DAYS.toHours(TimeUnit.SECONDS.toDays(totalDuration));
    long minutes = TimeUnit.SECONDS.toMinutes(totalDuration) - TimeUnit.HOURS.toMinutes(TimeUnit.SECONDS.toHours(totalDuration));
    long sec = TimeUnit.SECONDS.toSeconds(totalDuration) - TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(totalDuration));
    return (String.format("%d Days %d Hours %d Minutes %d Seconds", days, hours, minutes, sec));
  }

  public String getHumanReadableTimeDuration() {
    return getHumanReadableTimeDuration(totalTime);
  }

  public Component getFather() {
    return father;
  }

  public void setTotalTime(long totalTime) {
    this.totalTime = totalTime;
  }

  public long getTotalTime() {
    return totalTime;
  }

  public LocalTime getStartedTime() {
    return startTime;
  }

  public LocalTime getEndedTime() {
    return endTime;
  }

  public void setStartTime(LocalTime startTime) {
    this.startTime = startTime;
    if (father != null && father.getStartedTime() == null){
      father.setStartTime(startTime);
    }
  }

  public void setEndTime(LocalTime endTime) {
    this.endTime = endTime;
    if (father != null){
      father.setEndTime(endTime);
    }
  }

  public abstract void acceptVisitor(Visitor visitor);

  private void printTime() {
    System.out.println("Name "+"   Initial date " + "          Final date " + "                   Duration ");
    System.out.println(" " + this.name + "  +   " + this.startTime + "  " + this.endTime + "                 " + this.totalTime);
  }

  protected void addTimeDuration(long moreDuration) {
    totalTime = totalTime + moreDuration;
    printTime();
    if (father != null) {
      father.addTimeDuration(moreDuration);
    }
  }
}