package timeTracking;

import timeTracking.api.MenuInterface;
import timeTracking.impl.ConsoleMenu;

public class Main {
  public static void main(String[] args) throws Exception {
    MenuInterface menu = new ConsoleMenu();
    menu.start();
  }
}
