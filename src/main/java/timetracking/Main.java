package timetracking;

import timetracking.api.MenuInterface;
import timetracking.impl.ConsoleMenu;

public class Main {
  public static void main(String[] args) throws Exception {
    MenuInterface menu = new ConsoleMenu();
    menu.start();
  }
}
