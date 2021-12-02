import 'package:codelab_timetracker/add_projectOrtask.dart';
import 'package:codelab_timetracker/tree.dart';
import 'package:flutter/material.dart';

import 'PageIntervals.dart';
import 'formulario.dart';

class PageActivities extends StatefulWidget {
  @override
  _PageActivitiesState createState() => _PageActivitiesState();

}

class _PageActivitiesState extends State<PageActivities> {
  late Tree tree;

  @override
  void initState() {
    super.initState();
    tree = getTree();
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: Text('Inicio'), //Cambiado tree.root.name
          actions: <Widget>[
            IconButton(icon: Icon(Icons.home),
                onPressed: () {
                  while(Navigator.of(context).canPop()) {
                    print("POP");
                    Navigator.popUntil(context, ModalRoute.withName('/'));
                  }
                  PageActivities();
                }),
            IconButton(icon: Icon(Icons.document_scanner), onPressed: () {
              Navigator.of(context)
                  .push(MaterialPageRoute<void>(
                builder: (context) => RegisterPage(),
              ));
            })
          //TODO other actions
        ],
      ),

      body: ListView.separated(
        // it's like ListView.builder() but better
        // because it includes a separator between items
        padding: const EdgeInsets.all(16.0),
        itemCount: tree.root.children.length,
        itemBuilder: (BuildContext context, int index) =>
            _buildRow(tree.root.children[index], index),
        separatorBuilder: (BuildContext context, int index) =>
            const Divider(),
      ),
            floatingActionButton: FloatingActionButton(
              onPressed: () {
                Navigator.of(context)
                    .push(MaterialPageRoute<void>(
                    builder: (context) => AddComponent(),
                ));
              },
              child: Icon(Icons.add_circle_outline_sharp),
            ),
      ),
      );
  }

  void _navigateDownIntervals(int childId) {
    Navigator.of(context)
        .push(MaterialPageRoute<void>(builder: (context) => PageIntervals())
    );
  }

  Widget _buildRow(Activity activity, int index) {
    String strDuration = Duration(seconds: activity.duration).toString().split('.').first;
    // split by '.' and taking first element of resulting list
    // removes the microseconds part
    if (activity is Project) {
      return ListTile(
        title: Text('${activity.name}'),
        trailing: Text('$strDuration'),
        onTap: () => {},
        // TODO, navigate down to show children tasks and projects
      );
    } else if (activity is Task) {
      Task task = activity as Task;
      Widget trailing;
      trailing = Text('$strDuration');
      return ListTile(
        title: Text('${activity.name}'),
        trailing: trailing,
        onTap: () => _navigateDownIntervals(index),
        onLongPress: () {}, // TODO start/stop counting the time for tis task
      );




    } else {
      throw(Exception("Activity that is neither a Task or a Project"));
      // this solves the problem of return Widget is not nullable because an
      // Exception is also a Widget?
    }
  }
}