import 'dart:core';

import 'package:codelab_timetracker/page_activities.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class RegisterPage extends StatefulWidget {
  @override
  _RegisterPageState createState() => _RegisterPageState();
}







class _RegisterPageState extends State<RegisterPage> {
  GlobalKey<FormState> keyForm = new GlobalKey();
  TextEditingController  nameCtrl = new TextEditingController();
  TextEditingController  emailCtrl = new TextEditingController();
  TextEditingController  mobileCtrl = new TextEditingController();
  TextEditingController  passwordCtrl = new TextEditingController();
  TextEditingController  repeatPassCtrl = new TextEditingController();

  TextEditingController Period = new TextEditingController();
  TextEditingController From = new TextEditingController();
  TextEditingController To = new TextEditingController();
  TextEditingController Content = new TextEditingController();
  TextEditingController Format = new TextEditingController();





  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: new AppBar(
          title: new Text('Report'),
        ),

        body: new SingleChildScrollView(
          child: new Container(
            margin: new EdgeInsets.all(60.0),
            child: new Form(
              key: keyForm,
              child: formUI(),
            ),
          ),
        ),
    );
  }

  formItemsDesign(icon, item) {
    return Padding(
      padding: EdgeInsets.symmetric(vertical: 7),
      child: Card(child: ListTile(leading: Icon(icon), title: item)),
    );
  }

  String gender = 'hombre';


  Widget formUI() {
    return  Column(
      children: <Widget>[


    DropdownButton<String>(

      items: <String>['Last week', 'This week', 'Yesterday', 'Today', 'Other'].map((String value) {
        return DropdownMenuItem<String>(
          value: value,
          child: Text(value),
        );
      }).toList(),
      hint: const Text('Last week'),
      onChanged: (_) {},
    ),


        DropdownButton<String>(
          hint: const Text('Brief'),
          items: <String>['Brief', 'Detailed', 'Statistic'].map((String value) {
            return DropdownMenuItem<String>(
              value: value,
              child: Text(value),
            );
          }).toList(),
          onChanged: (_) {},
        ),


        DropdownButton<String>(
          hint: const Text('Web page'),
          items: <String>['Web page', 'PDF', 'Text'].map((String value) {
            return DropdownMenuItem<String>(
              value: value,
              child: Text(value),
            );
          }).toList(),
          onChanged: (_) {},
        ),

      Text(DateTime.now().toString()),
        RaisedButton(
          onPressed: () => _selectDate(context)
        ),],);
}



  Future<void> _selectDate(BuildContext context) async {
    DateTime currentDate = DateTime.now();
    final DateTime? pickedDate = await showDatePicker(
        context: context,
        initialDate: currentDate,
        firstDate: DateTime(2015),
        lastDate: DateTime(2050));
    if (pickedDate != null && pickedDate != currentDate)
      setState(() {
        Key ? pickedDate;
      });
  }
}




