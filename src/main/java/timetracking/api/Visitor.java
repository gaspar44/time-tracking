package timetracking.api;

import timetracking.core.Project;
import timetracking.core.Task;

public interface Visitor {

  void visitTask(Task task);

  void visitProject(Project project);
}
