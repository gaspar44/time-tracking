package timetracking.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import timetracking.api.Visitor;
import timetracking.core.Component;
import timetracking.core.Project;
import timetracking.core.Task;

public class TagSearcher implements Visitor {
  private List<Component> matchedComponents;
  private List<String> tagsToSearch;

  public TagSearcher() {
    System.out.println("creating new TagSearcher"); // DEBUG
    resetSearchElements();
  }

  private void resetSearchElements() {
    tagsToSearch = new ArrayList<>();
    matchedComponents = new ArrayList<>();
  }

  @Override
  public void visitTask(Task task) {
    System.out.println("TagSearcher visiting task: " + task.getName()); // DEBUG
    checkIfSearchTagsAreInComponentTags(task);
  }

  private void checkIfSearchTagsAreInComponentTags(Component component) {
    List<String> componentTags = component.getTags();
    if (componentTags == null) {
      System.out.println("no tags found for component: " + component.getName()); // DEBUG
      return;
    }

    if (componentTags.containsAll(tagsToSearch)) {
      matchedComponents.add(component);
      System.out.println("Component: " + component.getName()
          + " tags matched: " + tagsToSearch.toString()); // INFO
      return;
    }

    System.out.println("Component: " + component.getName() + " tags not matched"
        + " expected: " + Arrays.toString(tagsToSearch.toArray()) + " actual: "
        + Arrays.toString(componentTags.toArray())); // DEBUG;
  }

  public List<Component> getMatchedComponents() {
    System.out.println("obtaining results of search"); // DEBUG
    List<Component> componentListToReturn = matchedComponents;
    resetSearchElements();
    return componentListToReturn;
  }

  public void addSearchTag(String tag) {
    if (tag == null) {
      System.out.println("null tag passed "); // DEBUG
      return;
    }

    System.out.println("adding tags: " + tag); // DEBUG
    tagsToSearch.add(tag);
  }

  @Override
  public void visitProject(Project project) {
    System.out.println("TagSearcher visiting task: " + project.getName()); // INFO
    checkIfSearchTagsAreInComponentTags(project);
    List<Component> components = project.getComponents();
    System.out.println("Visiting components "); // INFO

    for (Component component : components) {
      component.acceptVisitor(this);
    }
  }
}
