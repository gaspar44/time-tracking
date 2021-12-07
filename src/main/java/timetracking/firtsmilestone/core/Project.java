package timetracking.firtsmilestone.core;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import timetracking.firtsmilestone.api.Visitor;
import timetracking.firtsmilestone.impl.JsonKeys;

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
    final int componentsSize = components.size();
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

  @Override
  public JSONObject toJson() {
    JSONObject jsonObject = new JSONObject();

    jsonObject.put(JsonKeys.NAME_KEY, this.getName());
    jsonObject.put(JsonKeys.TYPE_KEY, JsonKeys.PROJECT_TYPE);
    jsonObject.put(JsonKeys.DURATION_KEY, this.getTotalTime());
    jsonObject.put(JsonKeys.START_TIME_KEY, this.getStartedTime());
    jsonObject.put(JsonKeys.END_TIME_KEY, this.getEndedTime());
    jsonObject.put(JsonKeys.TAGS_KEY, this.getTags());
    jsonObject.put(JsonKeys.ID_KEY, this.getId());
    JSONArray jsonArray = new JSONArray();
    jsonObject.put(JsonKeys.COMPONENT_KEY, jsonArray);

    if (this.getFather() != null) {
      jsonObject.put(JsonKeys.FATHER_NAME, this.getFather().getName());
    }

    return jsonObject;
  }

  @Override
  public JSONObject toJson(int treeDeep) {
    if (treeDeep == -1) {
      return null;
    }

    final List<Component> components = this.getComponents();
    JSONObject jsonObject = this.toJson();
    JSONArray jsonComponents = new JSONArray();

    for (Component component : components) {
      JSONObject parsedComponent = component.toJson(treeDeep - 1);

      if (parsedComponent != null) {
        jsonComponents.put(parsedComponent);
      }
    }

    jsonObject.put(JsonKeys.COMPONENT_KEY, jsonComponents);
    return jsonObject;
  }

  @Override
  public Component findComponentById(int id) {
    logger.info("staring search by ID at component {}", this.getName());
    if (id == this.id) {
      logger.debug("found component");
      logger.trace("component with name {}", this.getName());
      return this;
    }

    final List<Component> components = this.getComponents();
    logger.debug("Looking at components");

    for (Component component : components) {
      Component isThis = component.findComponentById(id);

      if (isThis != null) {
        return isThis;
      }
    }
    logger.debug("nothing found");
    return null;
  }
}
