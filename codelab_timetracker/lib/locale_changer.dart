import 'package:flutter/material.dart';
import 'package:intl/intl.dart';

class LocaleChanger  {
  static Future<LocaleChanger> load(Locale locale) {
    final String name = locale.countryCode!.isEmpty
        ? locale.languageCode
        : locale.toString();
    final String localeName = Intl.canonicalizedLocale(name);

    return initializeMessages(localeName).then((b) {
      Intl.defaultLocale = localeName;
      return new MyLocalizations();
    });
  }

  static MyLocalizations of(BuildContext context) {
    return Localizations.of<MyLocalizations>(context, MyLocalizations);
  }

  static initializeMessages(String localeName) {}
}
