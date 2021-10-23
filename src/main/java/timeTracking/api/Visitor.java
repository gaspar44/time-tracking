package timeTracking.api;

import timeTracking.core.Project;
import timeTracking.core.Task;

public interface Visitor {

  void visitTask(Task task);

  void visitProject(Project project);
}
