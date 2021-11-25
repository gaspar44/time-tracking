package timetracking.firtsmilestone.impl;


import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import timetracking.firtsmilestone.api.Visitor;
import timetracking.firtsmilestone.core.Component;
import timetracking.firtsmilestone.core.Project;
import timetracking.firtsmilestone.core.Task;

/* This class, as its name says, prints the Tree of
 * components (see "Component" class) ordered by which
 * is the father of which. It also prints the tags of
 * each Component that has any associated to it.
 */
public class TreePrinter implements Visitor {
  private final Logger logger = LoggerFactory.getLogger(TreePrinter.class);
  private String fileName;
  private final List<String> tabulatorList = new ArrayList<String>();


  public TreePrinter() {
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

    if (father == null) {
      logger.info("Father of this component's tree is: {} ", project.getName());
      System.out.println("Father of this component's tree is: " +  project.getName());
    }

    for (Component component : components) {
      tabulatorList.add("+");

      String finalPlus = "";

      for (String plus : tabulatorList) {
        //logger.info(plus);
        finalPlus = String.join("", finalPlus, plus);
        //System.out.print(plus);
      }
      //logger.info(finalPlus);
      System.out.print(finalPlus);

      logger.info("{} {} is child of {}, tags: {}", finalPlus, component.getName(),
          project.getName(), component.getTags());

        System.out.println(component.getName() + " is child of " +
              project.getName() + " tags: " + component.getTags());

      component.acceptVisitor(this);
      tabulatorList.remove(tabulatorList.size() - 1);
    }
  }
}
