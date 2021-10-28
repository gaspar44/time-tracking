package timeTracking.impl;
import timeTracking.api.Visitor;
import timeTracking.core.Component;
import timeTracking.core.Project;
import timeTracking.core.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class TreePrinter implements Visitor {

  private String fileName;
  private List<String> tabulatorList = new ArrayList<String>();


  public TreePrinter(){
      this.fileName = "";
  }
  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  @Override
  public void visitTask(Task task) {
    this.fileName = "";
  }

  @Override
  public void visitProject(Project project) {

    Component father = project.getFather();
    List<Component> components = project.getComponents();

    if(father == null) {
      System.out.println(project.getName());
    }

    for ( Component component : components)
    {
      tabulatorList.add("+");

      for (String plus : tabulatorList)
      {
        System.out.print(plus);
      }

      System.out.println(component.getName() + "       child of " + project.getName());
      component.acceptVisitor(this);
      tabulatorList.remove(tabulatorList.size() - 1);
    }
  }
}
