package timetracking.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import timetracking.api.Visitor;
import timetracking.core.Component;
import timetracking.core.Project;
import timetracking.core.Task;

public class TagSearcher implements Visitor {
  private List<Component> matchedComponents;
  private List<String> tagsToSearch;
  private final Logger logger = LoggerFactory.getLogger(TagSearcher.class);

  public TagSearcher() {
    logger.debug("creating new TagSearcher");
    resetSearchElements();
  }

  private void resetSearchElements() {
    tagsToSearch = new ArrayList<>();
    matchedComponents = new ArrayList<>();
  }

  @Override
  public void visitTask(Task task) {
    logger.debug("TagSearcher visiting task: {} ", task.getName());
    checkIfSearchTagsAreInComponentTags(task);
  }

  private void checkIfSearchTagsAreInComponentTags(Component component) {
    List<String> componentTags = component.getTags();
    if (componentTags == null) {
      logger.debug("no tags found for component: {} ", component.getName());
      return;
    }

    if (componentTags.containsAll(tagsToSearch)) {
      matchedComponents.add(component);
      logger.info("Component: {} tags matched: {}", component.getName(),
          tagsToSearch.toString());
      return;
    }

    logger.debug("Component: {} tags not matched, expected: {} and actual: {}",
        component.getName(),
        Arrays.toString(tagsToSearch.toArray()),
        Arrays.toString(componentTags.toArray()));
  }


  public List<Component> getMatchedComponents() {
    logger.debug("obtaining results of search {}",matchedComponents);
    List<Component> componentListToReturn = matchedComponents;
    resetSearchElements();
    return componentListToReturn;
  }

  public void addSearchTag(String tag) {
    assert (tag != null);

    logger.debug("adding tags: {}", tag);
    tagsToSearch.add(tag);
  }

  @Override
  public void visitProject(Project project) {
    logger.info("TagSearcher visiting task: {}", project.getName());

    assert (tagsToSearch.size() != 0);

    checkIfSearchTagsAreInComponentTags(project);
    List<Component> components = project.getComponents();
    logger.info("Visiting components of {} ", project.getName());

    for (Component component : components) {
      component.acceptVisitor(this);
    }
  }
}
