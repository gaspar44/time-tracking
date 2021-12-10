import 'dart:core';
import 'package:codelab_timetracker/page_activities.dart';
import 'package:codelab_timetracker/tree.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/rendering.dart';
import 'package:flutter/widgets.dart';
import 'package:intl/intl.dart';

Widget getAppBarSearching(Function cancelSearch, Function searching,
    TextEditingController searchController) {
  return AppBar(
    automaticallyImplyLeading: false,
    leading: IconButton(
        icon: Icon(Icons.clear),
        onPressed: () {
          cancelSearch();
        }),
    title: Padding(
      padding: const EdgeInsets.only(bottom: 10, right: 10),
      child: TextField(
        controller: searchController,
        onEditingComplete: () {
          searching();
        },
        style: new TextStyle(color: Colors.white),
        cursorColor: Colors.white,
        autofocus: true,
        decoration: InputDecoration(
          focusColor: Colors.white,
          focusedBorder: UnderlineInputBorder(
              borderSide: BorderSide(color: Colors.white)),
          enabledBorder: UnderlineInputBorder(
              borderSide: BorderSide(color: Colors.white)),
        ),
      ),
    ),
  );
}