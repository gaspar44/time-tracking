package timeTracking.impl;
import timeTracking.api.Visitor;
import timeTracking.core.Component;
import timeTracking.core.Project;
import timeTracking.core.Task;
import java.util.List;


public class TreePrinter implements Visitor {

  //private TreePrinter sourceTree;
  private String fileName;
  private String maxTab = "+";
  private String tab = "+";

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

    Component father = project.getFather();
    List<Component> components = project.getComponents();

    if(father == null)
    {
      System.out.println(project.getName());
    }else
    {
      maxTab+=tab;
    }

    for ( Component component : components)
    {
      System.out.println(maxTab + component.getName());
      component.acceptVisitor(this);
    }


    }

}
