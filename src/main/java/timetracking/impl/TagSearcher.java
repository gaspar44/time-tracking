package timetracking.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import timetracking.api.Visitor;
import timetracking.core.Component;
import timetracking.core.Project;
import timetracking.core.Task;

/* Through a Visitor pattern, this class makes a Component
 * (see "Component" class) able to have a tag associated to
 * itself to make it distinguishable between other Components.
*/
public class TagSearcher implements Visitor {
  private List<Component> matchedComponents;
  private List<String> tagsToSearch;
  private final Logger logger = LoggerFactory.getLogger(TagSearcher.class);

  public TagSearcher() {
    logger.debug("creating new TagSearcher");
    resetSearchElements();
  }

  public TagSearcher(List<String> tagsToSearch) {
    this.tagsToSearch = tagsToSearch.stream().map(String::toLowerCase)
        .collect(Collectors.toList());
    logger.debug("creating new TagSearcher");
    logger.trace("added tags: {}", tagsToSearch);
    matchedComponents = new ArrayList<>();
  }

  private void resetSearchElements() {
    tagsToSearch = new ArrayList<>();
    matchedComponents = new ArrayList<>();
  }

  @Override
  public void visitTask(Task task) {
    logger.info("TagSearcher visiting task: {} ", task.getName());
    checkIfSearchTagsAreInComponentTags(task);
  }

  @Override
  public void visitProject(Project project) {
    logger.info("TagSearcher visiting task: {}", project.getName());

    checkIfSearchTagsAreInComponentTags(project);
    List<Component> components = project.getComponents();
    logger.info("Visiting components of {} ", project.getName());

    for (Component component : components) {
      component.acceptVisitor(this);
    }
  }


  public List<Component> getMatchedComponents() {
    logger.trace("obtaining results of search {}", matchedComponents);
    List<Component> componentListToReturn = matchedComponents;
    resetSearchElements();
    return componentListToReturn;
  }

  public void addSearchTag(String tag) {

    logger.debug("adding tags: {}", tag);
    tagsToSearch.add(tag.toLowerCase());
  }

  private void checkIfSearchTagsAreInComponentTags(Component component) {
    List<String> componentTags = component.getTags();
    if (componentTags == null) {
      logger.debug("no tags found for component: {} ", component.getName());
      return;
    }

    List<String> loweredComponentTags = componentTags.stream().map(String::toLowerCase)
        .collect(Collectors.toList());

    if (loweredComponentTags.containsAll(tagsToSearch)) {
      logger.info("match search for {}", component.getName());
      matchedComponents.add(component);

      logger.trace("Component: {} tags matched: {}", component.getName(),
          tagsToSearch.toString());
      return;
    }

    logger.trace("Component: {} tags not matched, expected: {} and actual: {}",
        component.getName(),
        Arrays.toString(tagsToSearch.toArray()),
        Arrays.toString(loweredComponentTags.toArray()));
  }

}
