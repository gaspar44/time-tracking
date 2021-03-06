package timetracking.thirdmilestone;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import timetracking.firtsmilestone.core.Component;
import timetracking.firtsmilestone.core.Project;
import timetracking.firtsmilestone.core.Task;
import timetracking.firtsmilestone.impl.DemoTree;
import timetracking.secondmilestone.impl.TagSearcher;


// Based on
// https://www.ssaurel.com/blog/create-a-simple-http-web-server-in-java
// http://www.jcgonzalez.com/java-socket-mini-server-http-example

public class WebServer {
  private static final int PORT = 8080; // port to listen to
  private final Logger logger = LoggerFactory.getLogger(WebServer.class);

  private Component root;
  private Component active;

  enum ComponentType {
    PROJECT,
    TASK
  }

  public WebServer(Component root) {
    this.root = root;
    active = root;
    try {
      ServerSocket serverConnect = new ServerSocket(PORT);
      logger.info("Server started.\nListening for connections on port : {} ...\n", PORT);
      // we listen until user halts server execution
      while (true) {
        // each client connection will be managed in a dedicated Thread
        new SocketThread(serverConnect.accept());
        // create dedicated thread to manage the client connection
      }
    } catch (IOException e) {
      logger.error("Server Connection error : {}", e.getMessage());
    }
  }

  public WebServer() {
    this(new Project("root", null));
  }

  private Component findComponentById(int id) {
    return root.findComponentById(id);
  }

  private class SocketThread extends Thread {
    // SocketThread sees WebServer attributes
    private final Socket insocked;
    // Client Connection via Socket Class

    SocketThread(Socket insocket) {
      this.insocked = insocket;
      this.start();
    }

    @Override
    public void run() {
      // we manage our particular client connection
      BufferedReader in;
      PrintWriter out;
      String resource;

      try {
        logger.info("connection arrived");
        // we read characters from the client via input stream on the socket
        in = new BufferedReader(new InputStreamReader(insocked.getInputStream()));
        // we get character output stream to client
        out = new PrintWriter(insocked.getOutputStream());
        // get first line of the request from the client
        String input = in.readLine();
        // we parse the request with a string tokenizer

        logger.debug("sockedthread : {}", input);

        StringTokenizer parse = new StringTokenizer(input);
        String method = parse.nextToken().toUpperCase();
        // we get the HTTP method of the client
        if (!method.equals("GET")) {
          logger.warn("501 Not Implemented : {} method", method);
        } else {
          // what comes after "localhost:8080"
          resource = parse.nextToken();
          logger.trace("input {} ", input);
          logger.trace("method  {}", method);
          logger.trace("resource {}", resource);

          parse = new StringTokenizer(resource, "/[?]=&");
          int i = 0;
          String[] tokens = new String[20];
          // more than the actual number of parameters
          while (parse.hasMoreTokens()) {
            tokens[i] = parse.nextToken();
            logger.trace("token {}={}", i, tokens[i]);
            i++;
          }

          // Make the answer as a JSON string, to be sent to the Javascript client
          String answer = makeHeaderAnswer() + makeBodyAnswer(tokens);
          logger.debug("answer\n{}", answer);
          // Here we send the response to the client
          out.println(answer);
          out.flush(); // flush character output stream buffer
        }

        in.close();
        out.close();
        insocked.close(); // we close socket connection
      } catch (Exception e) {
        logger.error("Exception : " + e);
      }
    }


    private String makeBodyAnswer(String[] tokens) {
      String body = "";
      switch (tokens[0]) {
        case "get_tree": {
          logger.debug("entry point: get_tree");
          int id = Integer.parseInt(tokens[1]);
          Component component = findComponentById(id);
          assert (component != null);
          body = component.toJson(1).toString();
          break;
        }

        case "start": {
          logger.debug("entry point: start");
          int id = Integer.parseInt(tokens[1]);
          Component component = findComponentById(id);
          assert (component != null);
          Task task = (Task) component;
          task.startNewInterval();
          body = "{}";
          break;
        }

        case "stop": {
          logger.debug("entry point: stop");
          int id = Integer.parseInt(tokens[1]);
          Component component = findComponentById(id);
          assert (component != null);
          Task task = (Task) component;
          task.stopActualInterval();
          body = "{}";
          break;
        }

        case "reset_demo": {
          logger.debug("reset to demo environment");
          DemoTree demo = new DemoTree();
          root = demo.getRootProject();
          active = root;
          break;
        }

        case "create_task": {
          logger.debug("entry point: create_task");
          body = createComponent(ComponentType.TASK, tokens);
          break;
        }

        case "create_project": {
          logger.debug("entry_point: create_project");
          body = createComponent(ComponentType.PROJECT, tokens);
          break;
        }

        case "search_by_tag": {
          logger.debug("entry_point: search_by_tag");

          List<String> tagsToSearch = List.of(tokens[1]);
          TagSearcher searcher = new TagSearcher(tagsToSearch);

          root.acceptVisitor(searcher);
          List<Component> obtainedResults = searcher.getMatchedComponents();

          JSONObject json = new JSONObject();
          JSONArray array = new JSONArray();

          for (Component obtainedResult : obtainedResults) {
            array.put(obtainedResult.toJson());
          }

          json.put("results", array);
          body = json.toString();

          logger.debug("BODY: " + body);
          break;
        }

        default:
          assert false;
      }

      logger.trace(body);
      return body;
    }

    private String createComponent(ComponentType type, String[] tokens) {
      String componentName = null;
      int fatherId = 0;
      List<String> tags = null;
      // Parsing HTTP parameters
      for (int i = 1; i < tokens.length; i++) {
        if (tokens[i] != null) {
          switch (tokens[i]) {
            case "component_name": {
              componentName = tokens[i + 1];
              logger.trace("component_name {}", componentName);
              i++;
              break;
            }

            case "father_id": {
              fatherId = Integer.parseInt(tokens [i + 1]);
              logger.trace("father_id: {}", fatherId);
              i++;
              break;
            }

            case "tags": {
              tags = List.of(tokens[i + 1].split(","));
              logger.debug("tags found");
              i++;
              break;
            }

            default:
              break;
          }
        }
      }

      Component component;
      Project father = (Project) root.findComponentById(fatherId);

      if (type.equals(ComponentType.TASK)) {
        logger.debug("creating Task");
        component = new Task(componentName, father);
      } else if (type.equals(ComponentType.PROJECT)) {
        logger.debug("creating project");
        component = new Project(componentName, father);
      } else {
        return null;
      }

      if (tags == null) {
        tags = new ArrayList<>();
      }

      component.setTags(tags);
      return component.toJson().toString();
    }

    private String makeHeaderAnswer() {
      logger.debug("generating HTTP header Answer");
      String answer = "";
      answer += "HTTP/1.0 200 OK\r\n";
      answer += "Content-type: application/json\r\n";
      answer += "\r\n";
      // blank line between headers and content, very important !
      return answer;
    }
  } // SocketThread

} // WebServer