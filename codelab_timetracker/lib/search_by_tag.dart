import 'dart:core';
import 'package:codelab_timetracker/requests.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/rendering.dart';
import 'package:flutter/widgets.dart';
import 'found_tags.dart';
import 'generated/l10n.dart';

class searchByTag extends StatefulWidget {

  @override
  _searchByTagState createState() => _searchByTagState();
}

class _searchByTagState extends State<searchByTag> {
  late String _tagToSearch;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(S.of(context).search_by_tag_app_text),
      ),
      body: Container(

        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceAround,
          children: [

            Row(
              children: <Widget> [
                Padding(padding: EdgeInsets.all(16.0)),
                Text(S.of(context).search_by_tag_write_text),
                Expanded(
                    child: TextField(
                      textAlign: TextAlign.center,
                      decoration: (InputDecoration(
                          hintText: S.of(context).search_by_tag_hint_text
                      )),
                      onChanged: (value) {
                        setState(() {
                          _tagToSearch = value;
                          searchTags(_tagToSearch);
                        });
                      },
                    )
                ),
              ],
            ),
          ],
        ),
      ),

        floatingActionButton:FloatingActionButton.extended(
        onPressed: () {
          Navigator.of(context)
              .push(MaterialPageRoute<void>(
            builder: (context) => TagsFounded(idFinal), //Meter ID DEL DECODED[RESULTS][0][ID]

          )
          );
        },
          icon: const Icon(Icons.saved_search),
           label: Text(S.of(context).search_by_tag_search_button_text),
      ),
    );
  }
}