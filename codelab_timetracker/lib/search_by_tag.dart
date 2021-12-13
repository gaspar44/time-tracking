import 'dart:core';
import 'package:codelab_timetracker/requests.dart';
import 'package:codelab_timetracker/tree.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/rendering.dart';
import 'package:flutter/widgets.dart';

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
        title: Text('Searcher by tag'),
      ),
      body: Container(

        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceAround,
          children: [

            Row(
              children: <Widget> [
                Padding(padding: EdgeInsets.all(16.0)),
                Text('Write a tag: '),
                Expanded(

                    child: TextField(

                      textAlign: TextAlign.center,
                      decoration: (const InputDecoration(
                          hintText: "Tag"
                      )),
                      onChanged: (value) {
                        setState(() {
                          _tagToSearch = value;
                        });
                      },

                    )
                ),
              ],
            ),


           // Text(searchTags(_tagToSearch));




          ],


        ),

      ),



      floatingActionButton:FloatingActionButton.extended(
        onPressed: () {
          searchTags(_tagToSearch);
        },

        icon: const Icon(Icons.saved_search),
        label: const Text("Search"),
      ),
    );
  }

  Widget _buildRow(Component activity){ //podria faltar index
    String strDuration = Duration(seconds: activity.duration)
        .toString()
        .split('.')
        .first;
    // split by '.' and taking first element of resulting list removes the microseconds part
      return (ListTile(
        title: Text(activity.name + ' - Project'),
        trailing: Text(strDuration),
        onTap: () => {},

        leading: IconButton(
            icon: const Icon(Icons.play_arrow), onPressed: () {}),
      ));
  }
}