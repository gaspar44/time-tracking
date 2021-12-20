import 'dart:core';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/rendering.dart';
import 'package:intl/intl.dart';


class RegisterPage extends StatefulWidget {
  @override
  _RegisterPageState createState() => _RegisterPageState();
}

class _RegisterPageState extends State<RegisterPage> {
  GlobalKey<FormState> keyForm = GlobalKey();
  TextEditingController period = TextEditingController();
  TextEditingController from = TextEditingController();
  TextEditingController to = TextEditingController();
  TextEditingController content = TextEditingController();
  TextEditingController format = TextEditingController();

  DateTime selectedTimeTo = DateTime.now();
  DateTime selectedTimeFrom = DateTime.now();

  final List<String> _periods = ['Last week', 'This week', 'Yesterday', 'Today', 'Other']; // Option 1
  String _selectedPeriod = 'Last week'; // Option 1

  final List<String> _contents = ['Brief', 'Detailed', 'Statistic']; // Option 2
  String _selectedContent = 'Brief'; // Option 2

  final List<String> _formats= ['Web page', 'PDF', 'Text']; // Option 3
  String _selectedFormat = 'Web page'; // Option 3

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: Text('Report'),
        ),
        body: Column(
           mainAxisAlignment: MainAxisAlignment.spaceAround,

            children: [

              Row(
                children: <Widget> [

                  Padding(padding: EdgeInsets.all(16.0)),
                  Text('Period'),

                  SizedBox(width: 40.0),  // Espacio entre titulo y opciones

                  DropdownButton<String>(
                    value: _selectedPeriod,
                    onChanged: (newValue) {
                      setState(() {
                        _selectedPeriod = newValue!;
                            _uploadCalendar(context);
                      });
                    },
                    items: _periods.map((value) {
                      return DropdownMenuItem(
                        child: new Text(value),
                        value: value,
                      );
                    }).toList(),
                  )
                ],
              ),


              Row(
                children: <Widget> [

                  Padding(padding: EdgeInsets.all(16.0)),
                  Text('From'),

                  SizedBox(width: 50.0),  // Espacio entre titulo y opciones

                  Text(DateFormat('yyyy-MM-dd').format(selectedTimeFrom)),

                  SizedBox(width: 10.0),  // Espacio entre titulo y opciones

                  IconButton(
                    icon: Icon(Icons.today, color: Colors.blue.shade400),
                    onPressed: () {
                      _selectDate(context);
                      _selectedPeriod = 'Other';
                    }
                  )
                ],
              ),


              Row(
                children: <Widget> [

                  Padding(padding: EdgeInsets.all(16.0)),
                  Text('To'),

                  SizedBox(width: 75.0),  // Espacio entre titulo y opciones

                  Text(DateFormat('yyyy-MM-dd').format(selectedTimeTo)),

                  SizedBox(width: 10.0),  // Espacio entre titulo y opciones

                  IconButton(
                          icon: Icon(Icons.today, color: Colors.blue.shade400),
                          onPressed: () => _selectFinalDate(context)
                  )
                ],
              ),


              Row(
                children: <Widget> [

                  Padding(padding: EdgeInsets.all(16.0)),
                  Text('Content'),

                  SizedBox(width: 27.0),  // Espacio entre titulo y opciones

                  DropdownButton<String>(
                        //isExpanded: true,
                        value: _selectedContent,
                        onChanged: (newValue) {
                          setState(() {
                            _selectedContent = newValue!;
                          });
                        },
                        items: _contents.map((location) {
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
                  Text('Format'),

                  SizedBox(width: 35.0),  // Espacio entre titulo y opciones

                  DropdownButton<String>(
                        //isExpanded: true,
                        value: _selectedFormat,
                        onChanged: (newValue) {
                          setState(() {
                            _selectedFormat = newValue!;
                          });
                        },
                        items: _formats.map((location) {
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

                    Padding(padding: EdgeInsets.all(8.0)),
                    Expanded(
                        child: FlatButton(
                            textColor: Colors.blue,
                            onPressed: () {},
                            child: Text('Generate')
                        )
                    ),
                  ]
              )
            ]
        )
    );
  }
/*
  formItemsDesign(icon, item) {
    return Padding(
      padding: EdgeInsets.symmetric(vertical: 7),
      child: Card(child: ListTile(leading: Icon(icon), title: item)),
    );
  }*/

  Future<void> _uploadCalendar(BuildContext context) async {

    DateTime today = DateTime.now();
    DateTime yesterday = today.subtract(Duration(days:1));

    DateTime mondayThisWeek = DateTime(today.year, today.month, today.day - today.weekday + 1);
    DateTime sundayThisWeek = mondayThisWeek.subtract(new Duration(days:-6));

    DateTime mondayLastWeek = mondayThisWeek.subtract(new Duration(days:7));
    DateTime sundayLastWeek = DateTime(today.year, today.month, today.day - today.weekday);

    switch(_selectedPeriod){

      case 'Today':
        selectedTimeFrom = today;
        selectedTimeTo = today;
        break;

      case 'Yesterday':
      selectedTimeFrom = yesterday;
      selectedTimeTo = yesterday;
        break;

      case 'This week':
        selectedTimeFrom = mondayThisWeek;
        selectedTimeTo = sundayThisWeek;
        break;

      case 'Last week':
        selectedTimeFrom = mondayLastWeek;
        selectedTimeTo = sundayLastWeek;
        break;

    }
  }

  Future<void> _selectDate(BuildContext context) async {
    DateTime currentDate = DateTime.now();

    final DateTime? pickedDate = await showDatePicker(
        context: context,
        initialDate: currentDate,
        firstDate: DateTime(currentDate.year -5),
        lastDate: DateTime(currentDate.year + 5));
    if (pickedDate != null && pickedDate != currentDate) {
      setState(() {
        selectedTimeFrom = pickedDate;
        get() => selectedTimeFrom;
      });
    }
  }

  Future<void> _selectFinalDate(BuildContext context) async {
    DateTime currentDate = DateTime.now();
    final DateTime? pickedDate = await showDatePicker(
        context: context,
        initialDate: currentDate,
        firstDate: DateTime(currentDate.year -5),
        lastDate: DateTime(currentDate.year + 5));
    if (pickedDate != null && pickedDate != currentDate) {
      setState(() {
        selectedTimeTo = pickedDate;
        get() => selectedTimeTo;

        if(pickedDate.difference(selectedTimeFrom) >= Duration(days: 0))
        {
          DateTimeRange rangeTimer = DateTimeRange(start: selectedTimeFrom, end: pickedDate);
        }else{
          _showAlert();
        }
      });
    }
  }

  Future<void> _showAlert() async {
    return showDialog<void>(
      context: context,
      barrierDismissible: false, // user must tap button!
      builder: (BuildContext context) {
        return AlertDialog(
          title: const Text('Alert'),
          content: SingleChildScrollView(
            child: ListBody(
              children: const <Widget>[
                Text('Error with the dates!'),
                Text('The last date can not be earlier that the beginning date! '),
              ],
            ),
          ),
          actions: <Widget>[
            TextButton(
              child: const Text('Change the dates.'),
              onPressed: () {
                Navigator.of(context).pop();
              },
            ),
          ],
        );
      },
    );
  }
}

