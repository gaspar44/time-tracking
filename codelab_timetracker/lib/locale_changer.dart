import 'package:flutter/material.dart';

class LocaleChanger with ChangeNotifier {
  Locale _currentLocale = new Locale("es");
  Locale get locale => _currentLocale;

  void changeLocale() {
    _currentLocale = _currentLocale.languageCode.toString() == "es"
        ? Locale("en") : Locale("es");

    notifyListeners();
  }
}