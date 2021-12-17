//import 'package:codelab_timetracker/locale_changer.txt';
import 'package:codelab_timetracker/locale_changer.dart';
import 'package:codelab_timetracker/page_activities.dart';
import 'package:flutter/material.dart';
import 'package:flutter_localizations/flutter_localizations.dart';

import 'package:flutter_gen/gen_l10n/app_localizations.dart';
import 'package:provider/provider.dart';

import 'generated/l10n.dart';


void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return ChangeNotifierProvider<LocaleChanger>(
      create: (context) => LocaleChanger(),
      child: Builder(
        builder: (context) {
          return MaterialApp(
            title: 'TimeTracker',
            theme: ThemeData(
              primarySwatch: Colors.blue,
              textTheme: const TextTheme(
                  subtitle1: TextStyle(fontSize: 20.0),
                  bodyText2: TextStyle(fontSize: 20.0)),
            ),
            home: PageActivities(0),
            locale: Provider.of<LocaleChanger>(context,listen: true).locale,
            localizationsDelegates: const [
              S.delegate,
              GlobalMaterialLocalizations.delegate,
              GlobalWidgetsLocalizations.delegate,
              GlobalCupertinoLocalizations.delegate,
            ],
            supportedLocales: S.delegate.supportedLocales,
          );
        }
      ),
    );
  }
}