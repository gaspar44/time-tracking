package timeTraking.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import timetracking.core.Component;
import timetracking.core.Project;
import timetracking.core.Task;
import timetracking.impl.TagSearcher;

import java.util.List;

public class TagSearcherTest {
  private static Project rootProject;
  private static Project softwareDesign;
  private static Project softwareTesting;
  private static Project dataBase;
  private static Project transPortation;
  private static Project problems;
  private static Project projectTimeTracke;

  private static Task firtslist;
  private static Task secondList;
  private static Task readHandle;
  private static Task firstMilestone;

  @BeforeAll
  public static void setup() throws Exception {
    rootProject = new Project("root", null);
    softwareDesign = new Project("software design", rootProject,"java","flutter");
    softwareTesting = new Project("software testing", rootProject,"c++","Java","python");
    dataBase = new Project("dataBasse",rootProject,"SQL","python","C++");
    transPortation = new Project("task transportation", rootProject);

    problems = new Project("problems", softwareDesign);
    projectTimeTracke = new Project("time tracker", softwareDesign);

    firtslist = new Task("firts list", problems,"java");
    secondList = new Task("Second list", problems,"Dart");
    readHandle = new Task("read handle",projectTimeTracke);

    firstMilestone = new Task("firstMilestone",projectTimeTracke,"Java","IntelliJ");
  }

  @Test
  public void searchForJavaTagsAtProject() throws Exception {
    String tagToSearch = "java";
    TagSearcher tagSearcher = new TagSearcher();
    tagSearcher.addSearchTag(tagToSearch);

    rootProject.acceptVisitor(tagSearcher);
    List<Component> componentList = tagSearcher.getMatchedComponents();
    Assertions.assertNotNull(componentList);
    Assertions.assertEquals(2,componentList.size());
    Assertions.assertTrue(componentList.contains(softwareDesign));
    Assertions.assertTrue(componentList.contains(firtslist));

    tagToSearch = "Java";
    tagSearcher.addSearchTag(tagToSearch);
    rootProject.acceptVisitor(tagSearcher);
    List<Component> newComponentList = tagSearcher.getMatchedComponents();
    Assertions.assertNotNull(newComponentList);
    Assertions.assertEquals(2,newComponentList.size());
    Assertions.assertNotEquals(newComponentList,componentList);
    Assertions.assertTrue(newComponentList.contains(firstMilestone));
    Assertions.assertTrue(newComponentList.contains(softwareTesting));
  }

  @Test
  public void searchForCppTagsAtProject() throws Exception {
    String tagToSearch = "C++";
    TagSearcher searcher = new TagSearcher();
    searcher.addSearchTag(tagToSearch);

    rootProject.acceptVisitor(searcher);
    List<Component> results = searcher.getMatchedComponents();
    Assertions.assertNotNull(results);
    Assertions.assertEquals(1,results.size());
    Assertions.assertEquals(results.get(0),dataBase);
  }
}
