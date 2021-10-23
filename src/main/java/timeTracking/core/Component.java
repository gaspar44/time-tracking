package timeTracking.core;

import timeTracking.api.Visitor;

import java.util.concurrent.TimeUnit;

public abstract class Component {
  private String name;
  protected Component father;
  protected long totalTime;

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


  public long getTotalTime() {
    return totalTime;
  }

  public abstract void acceptVisitor(Visitor visitor);

  protected void addTimeDuration(long moreDuration) {
    totalTime = totalTime + moreDuration;
    if (father != null) {
      father.addTimeDuration(moreDuration);
    }
  }
}