import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';


class page_report extends StatefulWidget {
  @override
  _page_reportState createState() => _page_reportState();

}


class _page_reportState extends State<page_report> {
  GlobalKey<FormState> keyForm= new GlobalKey();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Report'),



      )
    );
}



}

