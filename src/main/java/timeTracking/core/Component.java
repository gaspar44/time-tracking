package timeTracking.core;

import timeTracking.api.Visitor;

public abstract class Component {
  protected String name;
  protected Component father;

  protected Component(String name, Component father){
    this.father = father;
    this.name = name;
  }

  protected long getTotalTime() {
    return 0;
  }

  private void acceptVisitor(Visitor visitor) {

  }
}