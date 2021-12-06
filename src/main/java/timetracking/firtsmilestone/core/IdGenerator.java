package timetracking.firtsmilestone.core;

public class IdGenerator {
  private static IdGenerator instance;
  private static int actualId;

  public static IdGenerator getInstance() {
    if (instance == null) {
      instance = new IdGenerator();
    }
    return instance;
  }

  private IdGenerator() {
    actualId = 1;
  }

  public int getNextId() {
    return actualId;
  }

  public int generateId() {
    actualId = actualId + 1;
    return actualId - 1;
  }
}
