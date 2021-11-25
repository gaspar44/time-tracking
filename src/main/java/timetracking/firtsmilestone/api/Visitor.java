package timetracking.firtsmilestone.api;

import timetracking.firtsmilestone.core.Project;
import timetracking.firtsmilestone.core.Task;

public interface Visitor {

  void visitTask(Task task);

  void visitProject(Project project);
}
