package timeTracking.core;

import java.util.Date;
import java.util.Observable;
import java.util.Observer;

public class TimeInterval implements Observer {
  private Date startTime;
  private Date endTime;

  @Override
  public void update(Observable observable, Object o) {

  }
}
