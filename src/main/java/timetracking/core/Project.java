package timetracking.core;

import java.util.ArrayList;
import java.util.List;
import timetracking.api.Visitor;

public class Project extends Component {
  private List<Component> components;


  public Project(String root, Project father) {
    super(root, father);
    components = new ArrayList<>();

    if (father != null) {
      father.add(this);
    }
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
