package timeTracking.core;

import timeTracking.api.Visitor;
import java.awt.*;
import java.sql.Time;
import org.json.*;
import java.util.List;
import java.util.Date;

public abstract class Component {


  public String c_name;

  public Component(String name)
  {
    this.c_name = name;
  }

  public String getName() //Not abstract class.

  {
    return c_name;
  }

  
  public abstract long getTotalTime();
  public abstract void accept(JsonParser visitor);


}