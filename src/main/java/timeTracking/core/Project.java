package timeTracking.core;

import java.util.ArrayList;
import java.util.List;
public class Project extends Component{
  private List<Component> components;

  public Project(String root, Project father) {
    super(root,father);
    components = new ArrayList<>();

    if (father != null) {
      father.add(this);
    }
  }

  public List<Component> getComponents() {
    return components;
  }

  public void add(Component componentToAdd) {
    components.add(componentToAdd);
  }

  @Override
  public long getTotalTime() {
    for (Component component: components) {
      component.getTotalTime();
    }

    if (father != null) {
      father.addTimeDuration(totalTime);
    }

    return totalTime;
  }

  @Override
  public void accept(JsonParser visitor) {

  }
}
