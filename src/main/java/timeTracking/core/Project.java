package timeTracking.core;

import java.util.ArrayList;
import java.util.List;

public class Project extends Component{
  private List<Component> components;

  public Project(String root, Component father) {
    super(root,father);
    components = new ArrayList<>();
  }

  public List<Component> getComponents() {
    return components;
  }

  public void add(Component componentToAdd) {
    components.add(componentToAdd);
  }
}
