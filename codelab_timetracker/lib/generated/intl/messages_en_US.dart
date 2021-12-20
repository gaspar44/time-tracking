// DO NOT EDIT. This is code generated via package:intl/generate_localized.dart
// This is a library that provides messages for a en_US locale. All the
// messages from the main program should be duplicated here with the same
// function name.

// Ignore issues from commonly used lints in this file.
// ignore_for_file:unnecessary_brace_in_string_interps, unnecessary_new
// ignore_for_file:prefer_single_quotes,comment_references, directives_ordering
// ignore_for_file:annotate_overrides,prefer_generic_function_type_aliases
// ignore_for_file:unused_import, file_names, avoid_escaping_inner_quotes
// ignore_for_file:unnecessary_string_interpolations, unnecessary_string_escapes

import 'package:intl/intl.dart';
import 'package:intl/message_lookup_by_library.dart';

final messages = new MessageLookup();

typedef String MessageIfAbsent(String messageStr, List<dynamic> args);

class MessageLookup extends MessageLookupByLibrary {
  String get localeName => 'en_US';

  final messages = _notInlinedMessages(_notInlinedMessages);
  static Map<String, Function> _notInlinedMessages(_) => <String, Function>{
        "add_component_create": MessageLookupByLibrary.simpleMessage("create"),
        "add_component_hint_text": MessageLookupByLibrary.simpleMessage(
            "Write tags separated by \',\'"),
        "add_component_invalid_value_message":
            MessageLookupByLibrary.simpleMessage("Please enter some name."),
        "add_component_name": MessageLookupByLibrary.simpleMessage("Name: "),
        "add_component_select_type":
            MessageLookupByLibrary.simpleMessage("Select the type: \'"),
        "add_component_tags":
            MessageLookupByLibrary.simpleMessage("Associated Tags: "),
        "add_component_title":
            MessageLookupByLibrary.simpleMessage("Add Project or Task"),
        "home_title": MessageLookupByLibrary.simpleMessage("Home"),
        "page_intervals_from": MessageLookupByLibrary.simpleMessage("from "),
        "page_intervals_time_intervals":
            MessageLookupByLibrary.simpleMessage("Time intervals of "),
        "page_intervals_to": MessageLookupByLibrary.simpleMessage("to "),
        "search_by_tag_app_text":
            MessageLookupByLibrary.simpleMessage("Searcher by tag"),
        "search_by_tag_hint_text": MessageLookupByLibrary.simpleMessage("Tag"),
        "search_by_tag_search_button_text":
            MessageLookupByLibrary.simpleMessage("Search"),
        "search_by_tag_write_text":
            MessageLookupByLibrary.simpleMessage("Write a tag: ")
      };
}
