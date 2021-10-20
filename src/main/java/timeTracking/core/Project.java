package timeTracking.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
public class Project extends Component{
  private List<Component> components;
  private String ProjectName;
  private String ProjectID;

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

  @Override
  public long getTotalTime() {
      return 0;
  }

  @Override
  public String getName() {
    return ProjectName;
  }

  @Override
  public String getID() {
    return ProjectID;
  }

  @Override
  public void accept(JsonParser visitor) {

  }
}
