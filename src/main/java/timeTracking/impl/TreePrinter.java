package timeTracking.impl;
import timeTracking.api.Visitor;
import timeTracking.core.Component;
import timeTracking.core.Project;
import timeTracking.core.Task;
import java.util.List;


public class TreePrinter implements Visitor {
  @Override
  public void visitTask(Task task) {

  }

  @Override
  public void visitProject(Project project) {

    Component father = project.getFather();

    if (father == null)
    {
      List<Component> components = project.getComponents();

      for (Component component : components) {
        System.out.println(component.getName());
        component.acceptVisitor(this);
      }

    }else {

      father.acceptVisitor(this);
    }



  }
}
