import 'dart:convert' as convert;
import 'package:http/http.dart' as http;
import 'tree.dart';

final http.Client client = http.Client();
const String baseUrl = "http://10.0.2.2:8080";

Future<Tree> getTree(int id) async {
  var uri = Uri.parse("$baseUrl/get_tree?$id");
  // see https://pub.dev/packages/http for examples of use
  final response = await client.get(uri);
  // response is NOT a Future because of await but since getTree() is async,
  // execution continues (leaves this function) until response is available,
  // and then we come back here
  if (response.statusCode == 200) {
    // If the server did return a 200 OK response, then parse the JSON.
    Map<String, dynamic> decoded = convert.jsonDecode(response.body);
    return Tree(decoded);
  } else {
    // If the server did not return a 200 OK response, then throw an exception.
    print("statusCode=$response.statusCode");
    throw Exception('Failed to get children');
  }
}

Future<void> start(int id) async {
  var uri = Uri.parse("$baseUrl/start?$id");
  final response = await client.get(uri);
}

Future<void> stop(int id) async {
  var uri = Uri.parse("$baseUrl/stop?$id");
  final response = await client.get(uri);
}

Future<void> getNextId(int id) async {
  var uri = Uri.parse("$baseUrl/change_current?$id");
  final response = await client.get(uri);
}

Future<void> addComponent(String Name, String tags, String type, int fatherId) async {
  final String list_tags = tags.replaceAll(" ", "");
  var uri;
  uri = type == "Task" ? uri = Uri.parse("$baseUrl/create_task?component_name=$Name&father_id=$fatherId&tags&$list_tags")
      : uri = Uri.parse("$baseUrl/create_project?component_name=$Name&father_id=$fatherId&tags&$list_tags");
  final response = await client.get(uri);
}