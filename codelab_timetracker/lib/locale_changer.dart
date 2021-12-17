import 'dart:ui';
import 'package:flutter/material.dart';
class LocaleChanger {
  Locale _actualLocale = Locale("es");

  void changeLanguage(BuildContext context) {
    String actual = Localizations.localeOf(context).toString();

    _actualLocale = Locale("en");
  }

  Locale getLocale(){
    return _actualLocale;
  }
}