package timetracking.firtsmilestone.core;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import timetracking.firtsmilestone.api.Visitor;

/* This class is based on a kind of Component called "Project",
 * which can contain more "Projects" in each father Project or
 * have tasks inside these fathers as a list of Components.
*/

public class Project extends Component {
  private List<Component> components;
  private final Logger logger = LoggerFactory.getLogger(Project.class);

  public Project(String projectName, Project father) {
    super(projectName, father);
    init(father);
    logger.trace("Project {} created", projectName);
  }

  private void init(Project father) {
    components = new ArrayList<>();

    if (father != null) {
      logger.debug("adding new created project {} to father {}", this.getName(),
          father.getName());
      father.add(this);
    }
  }

  public Project(String projectName, Project father, String... tags) {
    super(projectName, father, tags);
    logger.trace("Project {} created", projectName);
    init(father);
  }

  public List<Component> getComponents() {
    return components;
  }

  public void setComponents(List<Component> components) {
    this.components = components;
  }

  public void add(Component componentToAdd) {
    int componentsSize = components.size();
    assert (componentToAdd != null);  //Precondition
    logger.trace("adding component {} to {}", componentToAdd.getName(), this.getName());
    components.add(componentToAdd);
    assert (components.size() == componentsSize + 1); // Postcondition
  }

  @Override
  public void acceptVisitor(Visitor visitor) {
    logger.debug("accepting visitor");
    visitor.visitProject(this);
  }

}
