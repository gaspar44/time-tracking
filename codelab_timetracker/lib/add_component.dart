import 'dart:core';
import 'package:codelab_timetracker/requests.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/rendering.dart';
import 'package:flutter/widgets.dart';

class AddComponent extends StatefulWidget {
  final int fatherId;
  AddComponent(this.fatherId);

  @override
  _AddComponentState createState() => _AddComponentState();
}

class _AddComponentState extends State<AddComponent> {
  final List<String> _types = ['Task', 'Project']; // Option 1
  String _selectedType = 'Task';

  late String _nameOfComponent;
  late String _associatedTags;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Add Project or Task'),
      ),
      body: Container(

        child: Column(
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
                Text('Name: '),
                Expanded(
                    child: TextFormField(
                        textAlign: TextAlign.center,
                        onChanged: (value) {
                          setState(() {
                            _nameOfComponent = value;
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
                      decoration: (const InputDecoration(
                          hintText: "Write tags separated by ','"
                      )),
                      onChanged: (value) {
                              setState(() {
                                _associatedTags = value;
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
          addComponent(_nameOfComponent, _associatedTags, _selectedType, widget.fatherId);
          while (Navigator.of(context).canPop()) {
            Navigator.of(context).pop();
          }
        },

        icon: const Icon(Icons.save),
        label: const Text("create"),
      ),
    );
  }
}