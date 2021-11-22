import 'dart:core';

import 'package:codelab_timetracker/page_activities.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class RegisterPage extends StatefulWidget {
  @override
  _RegisterPageState createState() => _RegisterPageState();
}



  class _RegisterPageCalendar extends State<RegisterPage> {
    DateTime currentDate = DateTime.now();

    Future<void> _selectDate(BuildContext context) async {
      final DateTime pickedDate = await showDatePicker(
          context: context,
          initialDate: currentDate,
          firstDate: DateTime(2015),
          lastDate: DateTime(2050));
      if (pickedDate != null && pickedDate != currentDate)
        setState(() {
          currentDate = pickedDate;
        });
    }

    @override
    Widget build(BuildContext context) {
      return Scaffold(
        appBar: AppBar(
          title: Text("DatePicker"),
        ),
        body: Center(
          child: Column(
            mainAxisSize: MainAxisSize.min,
            children: <Widget>[
              Text(currentDate.toString()),
              RaisedButton(
                onPressed: () => _selectDate(context),
                child: Text('Select date'),
              ),
            ],
          ),
        ),
      );
    }
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

  GlobalKey<DateTime> key = new DateTime.now();






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

      Text(key.toString()),
        RaisedButton(
          onPressed: () => _selectDate(context)
        ),


        formItemsDesign(
            Icons.phone,
            TextFormField(
              controller: mobileCtrl,
              decoration: new InputDecoration(
                labelText: 'Numero de telefono',
              ),
              keyboardType: TextInputType.phone,
              maxLength: 10,
              )),
        formItemsDesign(
            null,
            Column(children: <Widget>[
              Text("Genero"),
              RadioListTile<String>(
                title: const Text('Hombre'),
                value: 'hombre',
                groupValue: gender,
                onChanged: (value) {
                  setState(() {
                    gender = value!;
                  });
                },
              ),
              RadioListTile<String>(
                title: const Text('Mujer'),
                value: 'mujer',
                groupValue: gender,
                onChanged: (value) {
                  setState(() {
                    gender = value!;
                  });
                },
              )
            ])),
        formItemsDesign(
            Icons.email,
            TextFormField(
              controller: emailCtrl,
              decoration: new InputDecoration(
                labelText: 'Email',
              ),
              keyboardType: TextInputType.emailAddress,
              maxLength: 32,
              )),
        formItemsDesign(
            Icons.remove_red_eye,
            TextFormField(
              controller: passwordCtrl,
              obscureText: true,
              decoration: InputDecoration(
                labelText: 'Contraseña',
              ),
            )),
        formItemsDesign(
            Icons.remove_red_eye,
            TextFormField(
              controller: repeatPassCtrl,
              obscureText: true,
              decoration: InputDecoration(
                labelText: 'Repetir la Contraseña',
              ),
            )),
        GestureDetector(
            onTap: (){
              save();
            },child: Container(
          margin: new EdgeInsets.all(30.0),
          alignment: Alignment.center,
          decoration: ShapeDecoration(
            shape: RoundedRectangleBorder(
                borderRadius: BorderRadius.circular(30.0)),
            gradient: LinearGradient(colors: [
              Color(0xFF0EDED2),
              Color(0xFF03A0FE),
            ],
                begin: Alignment.topLeft, end: Alignment.bottomRight),
          ),
          child: Text("Guardar",
              style: TextStyle(
                  color: Colors.white,
                  fontSize: 18,
                  fontWeight: FontWeight.w500)),
          padding: EdgeInsets.only(top: 16, bottom: 16),
        ))
      ],
    );
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




  String? validatePassword(String value) {
    print("valorrr $value passsword ${passwordCtrl.text}");
    if (value != passwordCtrl.text) {
      return "Las contraseñas no coinciden";
    }
    return null;
  }

  String? validateName(String value) {
    String pattern = r'(^[a-zA-Z ]*$)';
    RegExp regExp = new RegExp(pattern);
    if (value.length == 0) {
      return "El nombre es necesario";
    } else if (!regExp.hasMatch(value)) {
      return "El nombre debe de ser a-z y A-Z";
    }
    return null;
  }

  String? validateMobile(String value) {
    String patttern = r'(^[0-9]*$)';
    RegExp regExp = new RegExp(patttern);
    if (value.length == 0) {
      return "El telefono es necesariod";
    } else if (value.length != 10) {
      return "El numero debe tener 10 digitos";
    }
    return null;
  }

  String? validateEmail(String value) {
    String pattern =
        r'^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$';
    RegExp regExp = new RegExp(pattern);
    if (value.length == 0) {
      return "El correo es necesario";
    } else if (!regExp.hasMatch(value)) {
      return "Correo invalido";
    } else {
      return null;
    }
  }

  save() {
    if (keyForm.currentState!.validate()) {
      print("Nombre ${nameCtrl.text}");
      print("Telefono ${mobileCtrl.text}");
      print("Correo ${emailCtrl.text}");
      keyForm.currentState!.reset();
    }
  }
}
