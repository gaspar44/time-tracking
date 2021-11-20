package timetracking.core;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import timetracking.api.Visitor;

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
    logger.trace("adding component {} to {}", componentToAdd.getName(), this.getName());
    components.add(componentToAdd);
  }

  @Override
  public void acceptVisitor(Visitor visitor) {
    logger.debug("accepting visitor");
    visitor.visitProject(this);
  }

}
