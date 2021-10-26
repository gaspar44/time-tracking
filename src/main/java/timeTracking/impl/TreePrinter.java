package timeTracking.impl;
import timeTracking.api.Visitor;
import timeTracking.core.Component;
import timeTracking.core.Project;
import timeTracking.core.Task;
import java.util.List;


public class TreePrinter implements Visitor {

  //private TreePrinter sourceTree;
  private String fileName;

  TreePrinter(){
      this.fileName = "";
  }

  @Override
  public void visitTask(Task task) {
    this.fileName = "";
  }


  public void setFileName(String fileName) {
    this.fileName = fileName;
  }


  @Override
  public void visitProject(Project project) {
    System.out.println("Entra en visitProject");

    Component father = project.getFather();

    if (father == null)
    {
      List<Component> components = project.getComponents();
      System.out.println(components);
      for (Component component : components) {
        System.out.println(project.getName());
        component.acceptVisitor(this);
      }
    }else {
      father.acceptVisitor(this);
    }
  }
}
