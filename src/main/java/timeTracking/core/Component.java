package timeTracking.core;

import timeTracking.api.Visitor;
import java.awt.*;
import java.sql.Time;
import org.json.*;
import java.util.List;

//Cada clase que dependa de Component deberá ser: public class Name implements Component{}
//https://www.baeldung.com/java-composite-pattern

public abstract class Component {
  private String name;
  private String ID;
  Component Components;

  protected List<Component> components;

  protected long getTotalTime() {

    return 0;
  }

  public String getName()
  {
    return name;
  }

  public String getID(){
    return ID;
  }

  public void accept(JsonParser visitor) {


  }

  private void acceptVisitor(Visitor visitor) {

  }


}