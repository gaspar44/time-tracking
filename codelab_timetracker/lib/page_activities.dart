import 'package:codelab_timetracker/add_component.dart';
import 'package:codelab_timetracker/locale_changer.dart';
import 'package:codelab_timetracker/project_intervals.dart';
import 'package:codelab_timetracker/search_by_tag.dart';
import 'package:codelab_timetracker/tree.dart' hide getTree;
import 'package:codelab_timetracker/requests.dart';
import 'package:flutter/material.dart';
import 'dart:async';
import 'package:flutter_localizations/flutter_localizations.dart';

import 'package:flutter_gen/gen_l10n/app_localizations.dart';

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
  LocaleChanger localeChanger = new LocaleChanger();
  static const int periodicRefresh = 2;
  Icon icono = Icon(Icons.play_arrow);

  void _activateTimer() {
    _timer = Timer.periodic(const Duration(seconds: periodicRefresh), (Timer t) {
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
                title: Text(AppLocalizations.of(context).helloWorld), //Cambiado tree.root.name
                actions: <Widget>[
                  IconButton(icon: const Icon(Icons.home),
                      onPressed: () {
                        while (Navigator.of(context).canPop()) {
                          print("POP");
                          Navigator.popUntil(context, ModalRoute.withName('/'));
                        }
                        PageActivities(0);
                      }),
                  IconButton(icon: const Icon(Icons.document_scanner), onPressed: () {
                    Navigator.of(context)
                        .push(MaterialPageRoute<void>(
                      builder: (context) => RegisterPage(),
                    ));
                  }),
                  IconButton(icon: const Icon(Icons.language), onPressed: () {
                    localeChanger.changeLanguage(context);
                  },),

                  IconButton(icon: const Icon(Icons.manage_search), onPressed: () {
                    Navigator.of(context)
                        .push(MaterialPageRoute<void>(
                      builder: (context) => searchByTag(),
                    ));
                  }
                  )

                ],
              ),

              body: ListView.separated(
                // it's like ListView.builder() but better
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
                  Navigator.of(context)
                      .push(MaterialPageRoute<void>(
                    builder: (context) => AddComponent(id),
                  ));
                },
                child: Icon(Icons.add_circle_outline_sharp),
              ),
            );
          } else if (snapshot.hasError) {
            return Text("${snapshot.error}");
          }
          return Container(
              height: MediaQuery
                  .of(context)
                  .size
                  .height,
              color: Colors.white,
              child: const Center(
                child: CircularProgressIndicator(),

              ));
        }
    );
  }

  void _navigateDownActivities(int childId) {
    _timer.cancel();
    Navigator.of(context)
        .push(MaterialPageRoute<void>(
      builder: (context) => PageActivities(childId),
    )).then((var value) {
      _activateTimer();
      _refresh();
    });
  }

  void _navigateDownIntervals(int childId) {
    _timer.cancel();
    Navigator.of(context)
        .push(MaterialPageRoute<void>(
      builder: (context) => PageIntervals(childId),
    )).then((var value) {
      _activateTimer();
      _refresh();
    });
  }

  void _navigateDownProjectIntervals(int projectID) {
    //_timer.cancel();
    Navigator.of(context)
        .push(MaterialPageRoute<void>(
      builder: (context) => ProjectIntervals(projectID),
    ));/*.then((var value) {
      _activateTimer();
      _refresh();
    });*/
  }


  Widget _buildRow(Component activity, int index) {
    String strDuration = Duration(seconds: activity.duration)
        .toString()
        .split('.')
        .first;
    // split by '.' and taking first element of resulting list removes the microseconds part
    if (activity is Project) {
      return (ListTile(
        title: Text(activity.name + ' - Project'),
        trailing: Text(strDuration),
        onTap: () => _navigateDownActivities(activity.id),
        leading: IconButton(
          icon: const Icon(Icons.info),
          onPressed: () {
            _navigateDownProjectIntervals(activity.id);
            },
        ),
      ));
    } else if (activity is Task) {
      // at the moment is the same, maybe changes in the future
      Widget trailing;
      trailing = Text(strDuration);
      return ListTile(
        title: ((Text(activity.name  + ' - Task'))
        ),

        trailing: trailing,
        onTap: () => _navigateDownIntervals(activity.id),
        leading: IconButton(
            icon: Icon((activity).active ? Icons.pause : Icons.play_arrow),
            onPressed: () {
              if ((activity).active) {
                stop(activity.id);
                _refresh();
              }else{
                start(activity.id);
                _refresh();
              }
            }
            ),
            );

    } else {
      throw(Exception("Activity that is neither a Task or a Project"));
      // this solves the problem of return Widget is not nullable because an
      // Exception is also a Widget?
    }
  }
}
