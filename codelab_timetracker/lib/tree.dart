// see Serializing JSON inside model classes in
// https://flutter.dev/docs/development/data-and-backend/json
import 'package:intl/intl.dart';
import 'dart:convert' as convert;

final DateFormat _dateFormatter = DateFormat("yyyy-MM-dd'T'HH:mm:ss");

abstract class Component {
  late final int id;
  late final String name;
  DateTime? initialDate;
  DateTime? finalDate;
  late final int duration;
  List<dynamic> children = List<dynamic>.empty(growable: true);

  Component(String componentName) {
    name = componentName;
    id = 0;
    duration = 0;
  }

  Component.fromJson(Map<String, dynamic> json)
      : id = json["ID"],
        name = json['name'],
        initialDate = json['start_time'] == null ? null : _dateFormatter.parse(json['start_time']),
        finalDate = json['end_time'] == null ? null : _dateFormatter.parse(json['end_time']),
        duration = json['duration'];
}

class Project extends Component {
  Project() : super("root");

  Project.fromJson(Map<String, dynamic> json) : super.fromJson(json) {

    if (json.containsKey('components')) {
      // json has only 1 level because depth=1 or 0 in time_tracker
      for (Map<String, dynamic> jsonChild in json['components']) {
        if (jsonChild['type'] == "Project") {
          children.add(Project.fromJson(jsonChild));
          // condition on key avoids infinite recursion
        } else if (jsonChild['type'] == "Task") {
          children.add(Task.fromJson(jsonChild));
        } else {
          assert(false);
        }
      }
    }
  }

  @override
  String toString() {
    return '${id}';
  }

  Future<String> getFather() async {
    return '${name}';
  }
}

class Task extends Component {
  bool active;
  Task.fromJson(Map<String, dynamic> json) :
        active = json['active'],
        super.fromJson(json) {
    for (Map<String, dynamic> jsonChild in json['time_intervals']) {
      children.add(Interval.fromJson(jsonChild));
    }
  }
}


class Interval {
  DateTime? initialDate;
  DateTime? finalDate;
  int duration;

  Interval.fromJson(Map<String, dynamic> json)
      : initialDate = json['start_time'] == null ? null : _dateFormatter.parse(json['start_time']),
        finalDate = json['end_time'] == null ? null : _dateFormatter.parse(json['end_time']),
        duration = json['current_duration'];

  @override
  String toString() {
    return '${initialDate}';
  }
}



class Tree {
  late Component root;

  Tree(Map<String, dynamic> dec) {
    // 1 level tree, root and children only, root is either Project or Task. If Project
    // children are Project or Task, that is, Activity. If root is Task, children are Instance.
    if (dec['type'] == "Project") {
      root = Project.fromJson(dec);
    } else if (dec['type'] == "Task") {
      root = Task.fromJson(dec);
    } else {
      assert(false, "neither project or task");
    }
  }

  Tree.fromList(List<dynamic> searchResultsList) {
    root = Project();
    root.children = searchResultsList;

    for (var i = 0; i < searchResultsList.length ; i ++){
      searchResultsList[i]["father_name"] == "root";
    }
  }
}


Tree getTree() {
  String strJson = "{"
      "\"name\":\"root\", \"class\":\"project\", \"id\":0, \"initialDate\":\"2020-09-22 16:04:56\", \"finalDate\":\"2020-09-22 16:05:22\", \"duration\":26,"
      "\"activities\": [ "
      "{ \"name\":\"software design\", \"class\":\"project\", \"id\":1, \"initialDate\":\"2020-09-22 16:05:04\", \"finalDate\":\"2020-09-22 16:05:16\", \"duration\":16 },"
      "{ \"name\":\"software testing\", \"class\":\"project\", \"id\":2, \"initialDate\": null, \"finalDate\":null, \"duration\":0 },"
      "{ \"name\":\"databases\", \"class\":\"project\", \"id\":3,  \"finalDate\":null, \"initialDate\":null, \"duration\":0 },"
      "{ \"name\":\"transportation\", \"class\":\"task\", \"id\":6, \"active\":false, \"initialDate\":\"2020-09-22 16:04:56\", \"finalDate\":\"2020-09-22 16:05:22\", \"duration\":10, \"intervals\":[] }"
      "] "
      "}";
  Map<String, dynamic> decoded = convert.jsonDecode(strJson);
  Tree tree = Tree(decoded);
  return tree;
}

testLoadTree() {
  Tree tree = getTree();
  print("root name ${tree.root.name}, duration ${tree.root.duration}");
  for (Component act in tree.root.children) {
    print("child name ${act.name}, duration ${act.duration}");
  }
}

Tree getTreeTask() {
  String strJson = "{"
      "\"name\":\"transportation\",\"class\":\"task\", \"id\":10, \"active\":false, \"initialDate\":\"2020-09-22 13:36:08\", \"finalDate\":\"2020-09-22 13:36:34\", \"duration\":10,"
      "\"intervals\":["
      "{\"class\":\"interval\", \"id\":11, \"active\":false, \"initialDate\":\"2020-09-22 13:36:08\", \"finalDate\":\"2020-09-22 13:36:14\", \"duration\":6 },"
      "{\"class\":\"interval\", \"id\":12, \"active\":false, \"initialDate\":\"2020-09-22 13:36:30\", \"finalDate\":\"2020-09-22 13:36:34\", \"duration\":4}"
      "]}";
  Map<String, dynamic> decoded = convert.jsonDecode(strJson);
  Tree tree = Tree(decoded);
  return tree;
}

void main() {
  testLoadTree();
}