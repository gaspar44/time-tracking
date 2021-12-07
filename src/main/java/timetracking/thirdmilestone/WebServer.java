package timetracking.thirdmilestone;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import timetracking.firtsmilestone.core.Component;
import timetracking.firtsmilestone.core.Project;
import timetracking.firtsmilestone.core.Task;
import timetracking.firtsmilestone.core.Timer;
import timetracking.firtsmilestone.impl.DemoTree;


// Based on
// https://www.ssaurel.com/blog/create-a-simple-http-web-server-in-java
// http://www.jcgonzalez.com/java-socket-mini-server-http-example

public class WebServer {
  private static final int PORT = 8080; // port to listen to
  private final Logger logger = LoggerFactory.getLogger(WebServer.class);

  private Component root;

  public WebServer(Component root) {
    this.root = root;
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
          logger.debug("answer\n{}",answer);
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
          break;
        }
        // TODO: add new task, project
        // TODO: edit task, project properties
        default:
          assert false;
      }
      logger.trace(body);
      return body;
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