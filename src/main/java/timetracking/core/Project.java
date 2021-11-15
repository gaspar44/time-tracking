package timetracking.core;

import java.util.ArrayList;
import java.util.List;
import timetracking.api.Visitor;

public class Project extends Component {
  private List<Component> components;

  public Project(String root, Project father) {
    super(root, father);
    init(father);
  }

  private void init(Project father) {
    components = new ArrayList<>();

    if (father != null) {
      father.add(this);
    }
  }

  public Project(String root, Project father, String... tags) {
    super(root, father, tags);
    init(father);
  }

  public List<Component> getComponents() {
    return components;
  }

  public void setComponents(List<Component> components) {
    this.components = components;
  }

  public void add(Component componentToAdd) {
    components.add(componentToAdd);
  }

  @Override
  public void acceptVisitor(Visitor visitor) {
    visitor.visitProject(this);
  }

}
