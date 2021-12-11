import 'dart:core';
import 'package:codelab_timetracker/page_activities.dart';
import 'package:codelab_timetracker/requests.dart';
import 'package:codelab_timetracker/tree.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/rendering.dart';
import 'package:flutter/widgets.dart';
import 'package:intl/intl.dart';


class AddComponent extends StatefulWidget {
  @override
  _AddComponentState createState() => _AddComponentState();
}

class _AddComponentState extends State<AddComponent> {

  final _formKey = GlobalKey<FormState>();

  DateTime selectedTimeTo = DateTime.now();
  DateTime selectedTimeFrom = DateTime.now();

  List<String> _types = ['Task', 'Project']; // Option 1
  String _selectedType = 'Task';
  late String _nameOfComponent;
  late String _associatedTags;
  String _nameType = '';




  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Add Project or Task'),
      ),

      body: new Container(

        child: new Column(
          mainAxisAlignment: MainAxisAlignment.spaceAround,

          children: [

            Row(
              children: <Widget> [

                Padding(padding: EdgeInsets.all(16.0)),
                Text('Select the type: '),

                SizedBox(width: 40.0),  // Espacio entre titulo y opciones

                DropdownButton<String>(
                  value: _selectedType,
                  onChanged: (newValue) {
                    setState(() {
                      _selectedType = newValue!;
                      print(_selectedType);
                    });
                  },
                  items: _types.map((location) {
                    return DropdownMenuItem(
                      child: new Text(location),
                      value: location,
                    );
                  }).toList(),
                )
              ],
            ),

            Row(

              children: <Widget> [
                Padding(padding: EdgeInsets.all(16.0)),
                Expanded(
                    child: Text('Father: '+ 'root')
                ),
              ],
            ),

            Row(

              children: <Widget> [
                Padding(padding: EdgeInsets.all(16.0)),
                Text('Name: '),
                Expanded(
                    child: TextFormField(
                        textAlign: TextAlign.center,
                        onChanged: (value) {
                          setState(() {
                            _nameOfComponent = value;
                            print(_nameOfComponent);
                          });
                        },
                        validator: (value) {
                          if (value!.isEmpty)
                            {
                              return 'Please enter some name.';
                            }
                        }
                    )

                )
                ,
              ],
            ),

            Row(
              children: <Widget> [
                Padding(padding: EdgeInsets.all(16.0)),
                Text('Associated Tags: '),
                Expanded(

                    child: TextField(

                      textAlign: TextAlign.center,
                      decoration: (InputDecoration(
                          hintText: "Write tags separated by ','"
                      )),
                      onChanged: (value) {
                              setState(() {
                                _associatedTags = value;
                            print(_associatedTags);
                        });
                      },

                    )
                ),
              ],
            )


          ],
        ),
      ),
      floatingActionButton:FloatingActionButton.extended(
        onPressed: () {
          addComponent( _nameOfComponent,  _associatedTags,  _selectedType);
        },
        icon: Icon(Icons.save),
        label: Text("Save"),
      ),

    );
  }






}

