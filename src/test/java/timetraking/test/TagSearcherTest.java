package timetraking.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import timetracking.firtsmilestone.core.Component;
import timetracking.firtsmilestone.core.Project;
import timetracking.firtsmilestone.core.Task;
import timetracking.firtsmilestone.impl.DemoTree;
import timetracking.secondmilestone.impl.TagSearcher;


public class TagSearcherTest {
  private static Project rootProject;
  private static Project softwareDesign;
  private static Project softwareTesting;
  private static Project dataBase;
  private static Project problems;
  private static Project projectTimeTracke;

  private static Task firtslist;
  private static Task transPortation;
  private static Task secondList;
  private static Task readHandle;
  private static Task firstMilestone;

  @BeforeAll
  public static void setup() {
    DemoTree demoTree = new DemoTree();
    rootProject = demoTree.getRootProject();
    softwareDesign = demoTree.getSoftwareDesign();
    softwareTesting = demoTree.getSoftwareTesting();
    dataBase = demoTree.getDataBase();
    transPortation = demoTree.getTransportation();

    problems = demoTree.getProblems();
    projectTimeTracke = demoTree.getProjectTimeTracke();

    firtslist = demoTree.getFirtslist();
    secondList = demoTree.getSecondList();
    readHandle = demoTree.getReadHandle();

    firstMilestone = demoTree.getFirstMilestone();
  }

  @Test
  public void searchForJavaTagsAtProjectTest() throws Exception {
    List<Component> expected = new ArrayList<>();
    expected.add(softwareDesign);
    expected.add(firtslist);
    expected.add(firstMilestone);
    expected.add(softwareTesting);

    String tagToSearch = "java";
    generalTest(expected, tagToSearch);
    tagToSearch = "Java";
    generalTest(expected, tagToSearch);
    tagToSearch = "JaVA";
    generalTest(expected, tagToSearch);
  }

  @Test
  public void searchForCppTagsAtProjectTest() throws Exception {
    List<Component> expected = new ArrayList<>();
    expected.add(softwareTesting);
    expected.add(dataBase);

    String tagToSearch = "C++";
    generalTest(expected, tagToSearch);

    tagToSearch = "c++";
    generalTest(expected, tagToSearch);
  }

  @Test
  public void searchNonexistentTagTest() {
    TagSearcher searcher = new TagSearcher();
    searcher.addSearchTag("Golang");

    rootProject.acceptVisitor(searcher);
    List<Component> results = searcher.getMatchedComponents();
    Assertions.assertNotNull(results);
    Assertions.assertEquals(0, results.size());
  }

  @Test
  public void searchForDartTagsAtProjectTest() throws Exception {
    String tagToSearch = "Dart";
    List<Component> expected = new ArrayList<>();
    expected.add(secondList);
    generalTest(expected, tagToSearch);
  }

  @Test
  public void searchForJavaAndFlutterTagsAtProjectTest() throws Exception {
    String tagToSearch1 = "java";
    String tagToSearch2 = "flutter";
    List<Component> expected = new ArrayList<>();
    expected.add(softwareDesign);
    generalTest(expected, tagToSearch1, tagToSearch2);
  }

  @Test
  public void searchForPythonTagsAtProjectTest() throws Exception {
    String tagToSearch1 = "python";
    String tagToSearch2 = "Java";
    String tagToSearch3 = "c++";
    List<Component> expected = new ArrayList<>();
    expected.add(softwareTesting);
    generalTest(expected, tagToSearch1, tagToSearch2, tagToSearch3);
  }

  @Test
  public void searchForDataBaseTest() throws Exception {
    String tagToSearch1 = "python";
    String tagToSearch2 = "SQL";
    String tagToSearch3 = "C++";
    List<Component> expected = new ArrayList<>();
    expected.add(dataBase);
    generalTest(expected, tagToSearch1, tagToSearch2, tagToSearch3);
  }

  @Test
  public void searchForMilestone() throws Exception {
    String tagToSearch1 = "Java";
    String tagToSearch2 = "IntelliJ";
    List<Component> expected = new ArrayList<>();
    expected.add(firstMilestone);
    generalTest(expected, tagToSearch1, tagToSearch2);
  }

  private void generalTest(List<Component> expected, String... tags) {
    List<String> tagsToSearch = Arrays.asList(tags);
    TagSearcher searcher = new TagSearcher(tagsToSearch);

    rootProject.acceptVisitor(searcher);
    List<Component> obtainedResults = searcher.getMatchedComponents();
    Assertions.assertNotNull(obtainedResults);

    System.out.println("obtained results: ");
    for (Component obtainedResult : obtainedResults) {
      System.out.println(obtainedResult.getName());
    }

    Assertions.assertEquals(expected.size(), obtainedResults.size());
    Assertions.assertEquals(expected, obtainedResults);
    Assertions.assertTrue(expected.containsAll(obtainedResults));
    Assertions.assertTrue(obtainedResults.containsAll(expected));
  }
}
