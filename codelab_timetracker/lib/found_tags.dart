import 'dart:async';

import 'package:codelab_timetracker/add_component.dart';
import 'package:codelab_timetracker/page_activities.dart';
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

class TagsFounded extends StatefulWidget {
  final int id;

  TagsFounded(this.id, {Key? key}) : super(key: key);
  @override
  _TagsFoundedState createState() => _TagsFoundedState();

}

class _TagsFoundedState extends State<TagsFounded> {
  late int id;
  late Future<Tree> futureTree;
  late Timer _timer;
  final _LocaleController = TextEditingController();
  Locale _userLocale = Locale("es");
  static const int periodicRefresh = 2;
  Icon icono = Icon(Icons.play_arrow);

  void _activateTimer() {
    _timer = Timer.periodic(const Duration(seconds: periodicRefresh), (Timer t) {
      futureTree = getTreeSearch();
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
    futureTree = getTreeSearch(); // to be used in build()
    setState(() {});
  }

  @override
  void initState() {
    super.initState();
    id = widget.id;
    futureTree = getTreeSearch();
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
                          //print(snapshot.data!.root.children[1]/*"POP"*/);
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
