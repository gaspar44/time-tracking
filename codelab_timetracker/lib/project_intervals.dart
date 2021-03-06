
import 'dart:async';

import 'package:codelab_timetracker/page_activities.dart';
import 'package:flutter/material.dart';
import 'package:codelab_timetracker/tree.dart' as Tree hide getTree;
import 'package:codelab_timetracker/requests.dart';
import 'package:intl/intl.dart';
import 'generated/l10n.dart';

class ProjectIntervals extends StatefulWidget {
  final int id;

  ProjectIntervals(this.id);

  @override
  _ProjectIntervalsState createState() => _ProjectIntervalsState();
}

class _ProjectIntervalsState extends State<ProjectIntervals> {
  late int id;
  late Future<Tree.Tree> futureTree;
  late Timer _timer;
  static const int periodicRefresh = 2;
  late Tree.Tree tree;

  void _activateTimer() {
    _timer = Timer.periodic(Duration(seconds: periodicRefresh), (Timer t) {
      futureTree = getTree(id);
      setState(() {});
    });
  }

  @override
  Widget builder(BuildContext context) {
    return Container();
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
    return FutureBuilder<Tree.Tree>(
      future: futureTree,
      // this makes the tree of children, when available, go into snapshot.data
      builder: (context, snapshot) {
        // anonymous function
        if (snapshot.hasData) {
          int numChildren = snapshot.data!.root.children.length;
          return Scaffold(
            appBar: AppBar(
              title: Text(S.of(context).page_intervals_time_intervals + snapshot.data!.root.name),
              actions: <Widget>[
                IconButton(icon: const Icon(Icons.home),
                    onPressed: () {
                      while(Navigator.of(context).canPop()) {
                        print("pop");
                        Navigator.of(context).pop();
                      }
                      /* this works also:
    Navigator.popUntil(context, ModalRoute.withName('/'));
  */
                      PageActivities(0);
                    }),
              ],
            ),
            body: ListView.separated(
              // it's like ListView.builder() but better because it includes a separator between items
              padding: const EdgeInsets.all(16.0),
              itemCount: 1,
              itemBuilder: (BuildContext context, int index) =>
                  _buildRow(snapshot.data!.root,S.of(context).locale_name),
              separatorBuilder: (BuildContext context, int index) =>
              const Divider(),
            ),
          );
        } else if (snapshot.hasError) {
          return Text("${snapshot.error}");
        }
        // By default, show a progress indicator
        return Container(
            height: MediaQuery.of(context).size.height,
            color: Colors.white,
            child: const Center(
              child: CircularProgressIndicator(),
            ));
      },
    );
  }
  Widget _buildRow(Tree.Component component,String localeName) {
    if (component.duration == 0) {
      return ListTile();
    }

    String strDuration = Duration(seconds: component.duration)
        .toString()
        .split('.')
        .first;
    String strInitialDate = parseTime(component.initialDate, localeName);
    String strFinalDate = parseTime(component.finalDate, localeName);
    return Card(
      //child: ,
      child: Column (
        children: <Widget> [
          ListTile(
            title: Text(
              S.of(context).page_intervals_from + strInitialDate + "\n" +
              S.of(context).page_intervals_to + strFinalDate
              ),
            trailing: Text(strDuration),
          ),
          Text(S.of(context).search_by_tag_hint_text + "\n" + component.tags.toString())
        ],
      )
    );
  }

  String parseTime(DateTime? time,String localeName) {
    // All this unnecessary and ridiculous stuff is because the not nullable that is at bottom of the dart libraries.
    DateFormat formatter = DateFormat.yMMMd();
    DateFormat hourFormatter = localeName == "en_US" ? DateFormat("hh:mm a") : DateFormat.Hms();
    String strDate = time.toString();
    DateTime date = DateTime.parse(strDate);
    return formatter.format(date) + " " + hourFormatter.format(date);
  }
}
