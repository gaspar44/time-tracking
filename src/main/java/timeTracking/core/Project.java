package timeTracking.core;

import java.util.ArrayList;
import java.util.List;

public class Project extends Component{
  private List<Component> components;
  private String projectname;
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
  protected String getName() {
    return projectname;
  }
  protected String getProjectID()
  {
    return ProjectID;
  }
}
