package timeTracking.api;

import timeTracking.core.Component;

public interface Visitor {

  void visitComponent(Component component);
}
