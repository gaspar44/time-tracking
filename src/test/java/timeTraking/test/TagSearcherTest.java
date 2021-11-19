package timeTraking.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import timetracking.core.Component;
import timetracking.core.Project;
import timetracking.core.Task;
import timetracking.impl.TagSearcher;

import java.util.Arrays;
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
  public void searchForJavaTagsAtProjectTest() throws Exception {
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
  public void searchForCppTagsAtProjectTest() throws Exception {
    String tagToSearch = "C++";
    generalTest(dataBase,1,tagToSearch);

    tagToSearch = "c++";
    generalTest(softwareTesting,1,tagToSearch);
  }

  @Test
  public void searchNonexistentTagTest() throws Exception {
    TagSearcher searcher = new TagSearcher();
    searcher.addSearchTag("Golang");

    rootProject.acceptVisitor(searcher);
    List<Component> results = searcher.getMatchedComponents();
    Assertions.assertNotNull(results);
    Assertions.assertEquals(0,results.size());
  }

  @Test
  public void searchForDartTagsAtProjectTest() throws Exception {
    String tagToSearch = "Dart";
    generalTest(secondList,1,tagToSearch);
  }

  @Test
  public void searchForJavaAndFlutterTagsAtProjectTest() throws Exception {
    String tagToSearch1 = "java";
    String tagToSearch2 = "flutter";
    generalTest(softwareDesign,1,tagToSearch1,tagToSearch2);
  }

  private void generalTest(Component expectedComponent, int expectedSize,String... tags) throws Exception {
    List<String> tagsToSearch = Arrays.asList(tags);
    TagSearcher searcher = new TagSearcher(tagsToSearch);

    rootProject.acceptVisitor(searcher);
    List<Component> newResults = searcher.getMatchedComponents();
    Assertions.assertNotNull(newResults);
    Assertions.assertEquals(expectedSize,newResults.size());
    Assertions.assertEquals(newResults.get(0),expectedComponent);
  }

  @Test
  public void searchForPythonTagsAtProjectTest() throws Exception {
    String tagToSearch1 = "python";
    String tagToSearch2 = "Java";
    String tagToSearch3 = "c++";
    generalTest(softwareTesting,1,tagToSearch1,tagToSearch2,tagToSearch3);

  }

  @Test
  public void searchFordataBasesTest() throws Exception {
    String tagToSearch1 = "python";
    String tagToSearch2 = "SQL";
    String tagToSearch3 = "C++";
    generalTest(dataBase,1,tagToSearch1,tagToSearch2,tagToSearch3);
  }

  @Test
  public void searchForMilestone() throws Exception {
    String tagToSearch1 = "Java";
    String tagToSearch2 = "IntelliJ";
    generalTest(firstMilestone, 1, tagToSearch1, tagToSearch2);
  }
}
