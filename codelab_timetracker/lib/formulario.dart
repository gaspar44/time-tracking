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

                  Row(
                  children: <Widget> [
                    Expanded(
                      child: Text('From'),
                    ),
                  Expanded(
                     child: Text(DateFormat('yyyy-MM-dd').format(selectedTimeFrom)),
                    ),
                    Expanded(
                      child: RaisedButton(
                          child: Icon(Icons.today),
                        onPressed: () => _selectDate(context)
                      )
                      )
                    ],
                  ),

                  Row(
                    children: <Widget> [
                      Expanded(
                        child: Text('To'),
                      ),
                      Expanded(
                        child: Text(DateFormat('yyyy-MM-dd').format(selectedTimeTo)),
                      ),
                      Expanded(

                          child: RaisedButton(
                             child: Icon(Icons.today),
                              onPressed: () => _selectFinalDate(context)
                          )
                      )

                    ],
                  ),


                  Row(
                    children: <Widget> [
                      Expanded(
                        child: Text('Content'),
                      ),
                      Expanded(
                          child: DropdownButton<String>(
                            isExpanded: true,
                            value: _selectedContent,
                            onChanged: (newValue) {
                              setState(() {
                                _selectedContent = newValue!;
                              });
                            },
                            items: _ContentList.map((location) {
                              return DropdownMenuItem(
                                child: new Text(location),
                                value: location,
                              );
                            }).toList(),
                          )
                      )
                    ],
                  ),


                  Row(
                    children: <Widget> [
                      Expanded(
                        child: Text('Format'),
                      ),
                      Expanded(
                          child: DropdownButton<String>(
                            isExpanded: true,
                            value: _selectedFormat,
                            onChanged: (newValue) {
                              setState(() {
                                _selectedFormat = newValue!;
                              });
                            },
                            items: _FormatList.map((location) {
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



  Future<void> _selectDate(BuildContext context) async {
    DateTime currentDate = DateTime.now();

    final DateTime? pickedDate = await showDatePicker(
        context: context,
        initialDate: currentDate,
        firstDate: DateTime(2015),
        lastDate: DateTime(2050));
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
        firstDate: DateTime(2015),
        lastDate: DateTime(2050));
    if (pickedDate != null && pickedDate != currentDate) {
      setState(() {
        selectedTimeTo = pickedDate;
        get() => selectedTimeTo;

        if(pickedDate.difference(selectedTimeFrom) >= Duration(days: 0))
        {
          DateTimeRange Hi = DateTimeRange(start: selectedTimeFrom, end: pickedDate);
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
                Text(' The last date can not be earlier that the beginning date! '),
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

