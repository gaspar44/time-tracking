package timeTracking.core;

import timeTracking.api.Visitor;
import java.awt.*;
import java.sql.Time;
import org.json.*;
import java.util.List;
import java.util.Date;

public abstract class Component {

  public abstract long getTotalTime();

  public abstract String getName();

  public abstract String getID();



  public abstract void accept(JsonParser visitor);



}