import 'package:codelab_timetracker/add_component.dart';
import 'package:codelab_timetracker/tree.dart' hide getTree;
import 'package:codelab_timetracker/requests.dart';
import 'package:flutter/material.dart';
import 'dart:async';
import 'page_intervals.dart';
import 'formulario.dart';

class PageActivities extends StatefulWidget {
  final int id;
  PageActivities(this.id, {Key? key}) : super(key: key);

  @override
  _PageActivitiesState createState() => _PageActivitiesState();
}

class _PageActivitiesState extends State<PageActivities> {
  late int id;
  late Future<Tree> futureTree;
  late Timer _timer;
  static const int periodicRefresh = 2;

  void _activateTimer() {
    _timer = Timer.periodic(Duration(seconds: periodicRefresh), (Timer t) {
      futureTree = getTree(id);
      setState(() {});
    });
  }

  void _refresh() async {
    futureTree = getTree(id); // to be used in build()
    setState(() {});
  }

  @override
  void initState() {
    super.initState();
    id = widget.id;
    futureTree = getTree(id);
    _activateTimer();
  }

  @override
  void dispose() {
    // "The framework calls this method when this State object will never build again"
    // therefore when going up
    _timer.cancel();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return FutureBuilder<Tree>(
        future: futureTree,
        builder: (context, snapshot) {
          if (snapshot.hasData) {
            return Scaffold(
              appBar: AppBar(
                title: const Text('Inicio'), //Cambiado tree.root.name
                actions: <Widget>[
                  IconButton(
                      icon: const Icon(Icons.home),
                      onPressed: () {
                        while (Navigator.of(context).canPop()) {
                          print("POP");
                          Navigator.popUntil(context, ModalRoute.withName('/'));
                        }
                        PageActivities(0);
                      }),
                  IconButton(
                      icon: const Icon(Icons.document_scanner),
                      onPressed: () {
                        Navigator.of(context).push(MaterialPageRoute<void>(
                          builder: (context) => RegisterPage(),
                        ));
                      })
                  //TODO other actions
                ],
              ),
              body: ListView.separated(
                // because it includes a separator between items
                padding: const EdgeInsets.all(16.0),
                itemCount: snapshot.data!.root.children.length,
                itemBuilder: (BuildContext context, int index) =>
                    _buildRow(snapshot.data!.root.children[index], index),

                separatorBuilder: (BuildContext context, int index) =>
                    const Divider(),
              ),
              floatingActionButton: FloatingActionButton(
                onPressed: () {
                  Navigator.of(context).push(MaterialPageRoute<void>(
                    builder: (context) => AddComponent(),
                  ));
                },
                child: const Icon(Icons.add_circle_outline_sharp),
              ),
            );
          } else if (snapshot.hasError) {
            return Text("${snapshot.error}");
          }
          return Container(
              height: MediaQuery.of(context).size.height,
              color: Colors.white,
              child: const Center(
                child: CircularProgressIndicator(),
              ));
        });
  }

  void _navigateDownActivities(int childId) {
    _timer.cancel();
    Navigator.of(context)
        .push(MaterialPageRoute<void>(
      builder: (context) => PageActivities(childId),
    ))
        .then((var value) {
      _activateTimer();
      _refresh();
    });
  }

  void _navigateDownIntervals(int childId) {
    _timer.cancel();
    Navigator.of(context)
        .push(MaterialPageRoute<void>(
      builder: (context) => PageIntervals(childId),
    ))
        .then((var value) {
      _activateTimer();
      _refresh();
    });
  }

  Widget _buildRow(Component activity, int index) {
    String strDuration =
        Duration(seconds: activity.duration).toString().split('.').first;
    if (activity is Project) {
      if (('${activity.children}').isNotEmpty) {
        getNextId(activity.id);
        return ListTile(
          title: (ExpansionTile(
            title: Text(activity.name + ' - Project'),
            children: const <Widget>[
              //ListTile(title: Text(('${activity.children}').toString()))
            ],
          )),
        );
      } else {
        return ListTile(
          title: (ExpansionTile(
            title: Text(activity.name + ' - Project'),
            children: const <Widget>[
              ListTile(
                  title: Text('There are not available projects or tasks.'))
            ],
          )),
        );
      }
    } else if (activity is Task) {
      // at the moment is the same, maybe changes in the future
      Widget trailing;
      trailing = Text(strDuration);
      return ListTile(
        title: (ExpansionTile(
            title: Text(activity.name + ' - Task'),
            children: <Widget>[
              ListTile(title: Text(('${activity.children}').toString()))
            ])),
        trailing: trailing,
        onTap: () => _navigateDownIntervals(activity.id),
        onLongPress: () {
          if ((activity).active) {
            stop(activity.id);
            _refresh();
          } else {
            start(activity.id);
            _refresh();
          }
        },
      );
    } else {
      throw (Exception("Activity that is neither a Task or a Project"));
      // this solves the problem of return Widget is not nullable because an
      // Exception is also a Widget?
    }
  }
}