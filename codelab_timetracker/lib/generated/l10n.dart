// GENERATED CODE - DO NOT MODIFY BY HAND
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'intl/messages_all.dart';

// **************************************************************************
// Generator: Flutter Intl IDE plugin
// Made by Localizely
// **************************************************************************

// ignore_for_file: non_constant_identifier_names, lines_longer_than_80_chars
// ignore_for_file: join_return_with_assignment, prefer_final_in_for_each
// ignore_for_file: avoid_redundant_argument_values, avoid_escaping_inner_quotes

class S {
  S();

  static S? _current;

  static S get current {
    assert(_current != null,
        'No instance of S was loaded. Try to initialize the S delegate before accessing S.current.');
    return _current!;
  }

  static const AppLocalizationDelegate delegate = AppLocalizationDelegate();

  static Future<S> load(Locale locale) {
    final name = (locale.countryCode?.isEmpty ?? false)
        ? locale.languageCode
        : locale.toString();
    final localeName = Intl.canonicalizedLocale(name);
    return initializeMessages(localeName).then((_) {
      Intl.defaultLocale = localeName;
      final instance = S();
      S._current = instance;

      return instance;
    });
  }

  static S of(BuildContext context) {
    final instance = S.maybeOf(context);
    assert(instance != null,
        'No instance of S present in the widget tree. Did you add S.delegate in localizationsDelegates?');
    return instance!;
  }

  static S? maybeOf(BuildContext context) {
    return Localizations.of<S>(context, S);
  }

  /// `es_VE`
  String get locale_name {
    return Intl.message(
      'es_VE',
      name: 'locale_name',
      desc: '',
      args: [],
    );
  }

  /// `Inicio`
  String get home_title {
    return Intl.message(
      'Inicio',
      name: 'home_title',
      desc: '',
      args: [],
    );
  }

  /// `crear`
  String get add_component_create {
    return Intl.message(
      'crear',
      name: 'add_component_create',
      desc: '',
      args: [],
    );
  }

  /// ` Agregar Proyecto o Tarea`
  String get add_component_title {
    return Intl.message(
      ' Agregar Proyecto o Tarea',
      name: 'add_component_title',
      desc: '',
      args: [],
    );
  }

  /// `Seleccionar tipo`
  String get add_component_select_type {
    return Intl.message(
      'Seleccionar tipo',
      name: 'add_component_select_type',
      desc: '',
      args: [],
    );
  }

  /// `Nombre : `
  String get add_component_name {
    return Intl.message(
      'Nombre : ',
      name: 'add_component_name',
      desc: '',
      args: [],
    );
  }

  /// `Por favor, ingrese un nombre.`
  String get add_component_invalid_value_message {
    return Intl.message(
      'Por favor, ingrese un nombre.',
      name: 'add_component_invalid_value_message',
      desc: '',
      args: [],
    );
  }

  /// `Etiquetas asociadas`
  String get add_component_tags {
    return Intl.message(
      'Etiquetas asociadas',
      name: 'add_component_tags',
      desc: '',
      args: [],
    );
  }

  /// `Etiquetas separadas por ,`
  String get add_component_hint_text {
    return Intl.message(
      'Etiquetas separadas por ,',
      name: 'add_component_hint_text',
      desc: '',
      args: [],
    );
  }

  /// `Intervalos de  `
  String get page_intervals_time_intervals {
    return Intl.message(
      'Intervalos de  ',
      name: 'page_intervals_time_intervals',
      desc: '',
      args: [],
    );
  }

  /// `desde `
  String get page_intervals_from {
    return Intl.message(
      'desde ',
      name: 'page_intervals_from',
      desc: '',
      args: [],
    );
  }

  /// `hasta `
  String get page_intervals_to {
    return Intl.message(
      'hasta ',
      name: 'page_intervals_to',
      desc: '',
      args: [],
    );
  }

  /// `Búsqueda por etiqueta`
  String get search_by_tag_app_text {
    return Intl.message(
      'Búsqueda por etiqueta',
      name: 'search_by_tag_app_text',
      desc: '',
      args: [],
    );
  }

  /// `Escriba una etiqueta: `
  String get search_by_tag_write_text {
    return Intl.message(
      'Escriba una etiqueta: ',
      name: 'search_by_tag_write_text',
      desc: '',
      args: [],
    );
  }

  /// `Etiqueta`
  String get search_by_tag_hint_text {
    return Intl.message(
      'Etiqueta',
      name: 'search_by_tag_hint_text',
      desc: '',
      args: [],
    );
  }

  /// `Buscar`
  String get search_by_tag_search_button_text {
    return Intl.message(
      'Buscar',
      name: 'search_by_tag_search_button_text',
      desc: '',
      args: [],
    );
  }

  /// `Proyecto`
  String get project_name {
    return Intl.message(
      'Proyecto',
      name: 'project_name',
      desc: '',
      args: [],
    );
  }

  /// `Tarea`
  String get task_name {
    return Intl.message(
      'Tarea',
      name: 'task_name',
      desc: '',
      args: [],
    );
  }
}

class AppLocalizationDelegate extends LocalizationsDelegate<S> {
  const AppLocalizationDelegate();

  List<Locale> get supportedLocales {
    return const <Locale>[
      Locale.fromSubtags(languageCode: 'es', countryCode: 'VE'),
      Locale.fromSubtags(languageCode: 'en', countryCode: 'US'),
    ];
  }

  @override
  bool isSupported(Locale locale) => _isSupported(locale);
  @override
  Future<S> load(Locale locale) => S.load(locale);
  @override
  bool shouldReload(AppLocalizationDelegate old) => false;

  bool _isSupported(Locale locale) {
    for (var supportedLocale in supportedLocales) {
      if (supportedLocale.languageCode == locale.languageCode) {
        return true;
      }
    }
    return false;
  }
}
