import 'dart:core';
import 'package:codelab_timetracker/page_activities.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';

class RegisterPage extends StatefulWidget {
  @override
  _RegisterPageState createState() => _RegisterPageState();
}

class _RegisterPageState extends State<RegisterPage> {
  GlobalKey<FormState> keyForm = new GlobalKey();
  TextEditingController Period = new TextEditingController();
  TextEditingController From = new TextEditingController();
  TextEditingController To = new TextEditingController();
  TextEditingController Content = new TextEditingController();
  TextEditingController Format = new TextEditingController();

  DateTime selectedTimeTo = DateTime.now();
  DateTime selectedTimeFrom = DateTime.now();

  List<String> _locations = ['Last week', 'This week', 'Yesterday', 'Today', 'Other']; // Option 1
  String _selectedLocation = 'Last week'; // Option 1

  List<String> _ContentList = ['Brief', 'Detailed', 'Statistic']; // Option 2
  String _selectedContent = 'Brief'; // Option 2

  List<String> _FormatList= ['Web page', 'PDF', 'Text']; // Option 3
  String _selectedFormat = 'Web page'; // Option 3

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Report'),
      ),
      body: new Container(
        child: new Column(
          children: [
            Row(
              children: <Widget> [
                Expanded(
                  child: Text('Period'),
                ),

                Expanded(
                    child: DropdownButton<String>(
                      isExpanded: true,
                      value: _selectedLocation,
                      onChanged: (newValue) {
                        setState(() {
                          _selectedLocation = newValue!;
                        });
                      },
                      items: _locations.map((location) {
                        return DropdownMenuItem(
                          child: new Text(location),
                          value: location,
                        );
                      }).toList(),
                    )
                )
              ],
            ),
          ]
        )
      )
    );
  }

  formItemsDesign(icon, item) {
    return Padding(
      padding: EdgeInsets.symmetric(vertical: 7),
      child: Card(child: ListTile(leading: Icon(icon), title: item)),
    );
  }
}


