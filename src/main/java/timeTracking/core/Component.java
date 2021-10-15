package timeTracking.core;

import timeTracking.api.Visitor;

import java.util.List;

public abstract class Component {
  private String name;
  protected List<Component> components;

  protected long getTotalTime() {
    return 0;
  }

  private void acceptVisitor(Visitor visitor) {

  }

}