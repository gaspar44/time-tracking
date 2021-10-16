package timeTracking;

public class Main {
  private static String readFromJson;
  private static String saveToJson;

  private static void parseFlags(String[] args) {
    int len = args.length;

    if (len % 2 != 0) {
      System.out.println("insuficient arguments");
      System.out.println("usage: \n --save-to-json: Stores the tree at specified json.\n--read-from-json: Reads from specified file if not found a new one will be created");
      System.exit(1);
    }

    for (int i = 0; i < len; i++){
      if (args[i].equals("--save-to-json")) {
          saveToJson = args[i+1];
          i++;
      }
      else if (args[i].equals("--read-from-json")){
        readFromJson = args[i+1];
        i++;
      }

      else {
        System.out.println("unknow option: " + args[i]);
        System.exit(1);
      }
    }

    saveToJson = saveToJson == null ? readFromJson : saveToJson;
  }

  public static void main(String[] args) {
    parseFlags(args);
  }
}
