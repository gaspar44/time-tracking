import 'dart:async';

import 'package:codelab_timetracker/add_component.dart';
import 'package:codelab_timetracker/project_intervals.dart';
import 'package:codelab_timetracker/requests.dart';
import 'package:codelab_timetracker/search_by_tag.dart';
import 'package:codelab_timetracker/tree.dart' hide getTree;
import 'package:flutter/material.dart';
import 'package:provider/src/provider.dart';

import 'formulario.dart';
import 'generated/l10n.dart';
import 'locale_changer.dart';
import 'page_intervals.dart';

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
  final _LocaleController = TextEditingController();
  Locale _userLocale = Locale("es");
  static const int periodicRefresh = 2;
  Icon icono = Icon(Icons.play_arrow);

  void _activateTimer() {
    _timer = Timer.periodic(const Duration(seconds: periodicRefresh), (Timer t) {
      futureTree = getTree(id);
      setState(() {});
    });
  }

  @override
  void didChangeDependencies() {
    final newLocale = Localizations.localeOf(context);

    if (newLocale != _userLocale) {
      _LocaleController.clear();

    }
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
                title: Text(S.of(context).home_title), //Cambiado tree.root.name
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
                    context.read<LocaleChanger>().changeLocale();
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

              body: GridView.builder(
                // it's like ListView.builder() but better
                // because it includes a separator between items
                padding: const EdgeInsets.all(16.0),
                itemCount: snapshot.data!.root.children.length,
                
                itemBuilder: (BuildContext context, int index) =>
                    _buildRow(snapshot.data!.root.children[index], index),
                 gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(crossAxisCount: 1,
                   childAspectRatio: 4, mainAxisSpacing: 16,

                 ),

              ),
              floatingActionButton: FloatingActionButton(
                onPressed: () {
                  Navigator.of(context)
                      .push(MaterialPageRoute<void>(
                    builder: (context) => AddComponent(id),
                  ));
                },
                child: const Icon(Icons.add_circle_outline_sharp),
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

    final Color colorProject = Colors.lightBlueAccent.withOpacity(0.5);
    final Color colorTask = Colors.lightGreenAccent.withOpacity(0.5);
    String strDuration = Duration(seconds: activity.duration)
        .toString()
        .split('.')
        .first;
    // split by '.' and taking first element of resulting list removes the microseconds part
    if (activity is Project) {
      return (ListTile(

        shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(100.0)),
        title: Text('\n' + activity.name + '\n  Project',
        style: TextStyle(fontWeight: FontWeight.bold),
          textAlign: TextAlign.center,),
        trailing: Text('\n'+strDuration),
        
        tileColor: colorProject,
        onTap: () => _navigateDownActivities(activity.id),
        leading: IconButton(
          alignment: Alignment.center,
          icon: const Icon(Icons.info),
          //trailing: "gola",
          onPressed: () {
            _navigateDownProjectIntervals(activity.id);
            },
        ),
      ));
    } else if (activity is Task) {
      // at the moment is the same, maybe changes in the future
      Widget trailing;
      trailing = Text('\n'+strDuration);
      return ListTile(


        shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(100.0)),
        title: Text('\n' + activity.name + '\n  Task',
          style: TextStyle(fontWeight: FontWeight.bold),
          textAlign: TextAlign.center,
        ),

        trailing: trailing,
        tileColor: colorTask,
        onTap: () => _navigateDownIntervals(activity.id),
        leading: IconButton(
            alignment: Alignment.center,
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
