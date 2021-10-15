package timeTraking.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import timeTracking.api.MenuInterface;
import timeTracking.impl.ConsoleMenu;

public class ConsoleMenuTest {
  private static MenuInterface menu;

  @BeforeAll
  public static void setup() throws Exception {
    menu = new ConsoleMenu();
  }

  @Test
  public void getTaskTimeTest() throws Exception {
    long time = menu.getTaskTime();
    Assertions.assertEquals(0,time);
  }
}
