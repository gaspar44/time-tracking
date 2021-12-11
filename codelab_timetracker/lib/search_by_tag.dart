import 'dart:core';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/rendering.dart';
import 'package:flutter/widgets.dart';

Widget getAppBarSearching(Function cancelSearch, Function searching,
    TextEditingController searchController) {
  return AppBar(
    automaticallyImplyLeading: false,
    leading: IconButton(
        icon: const Icon(Icons.clear),
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
        style: const TextStyle(color: Colors.white),
        cursorColor: Colors.white,
        autofocus: true,
        decoration: const InputDecoration(
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