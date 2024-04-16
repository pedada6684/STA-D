import 'package:flutter/material.dart';
import 'package:stad/component/app_bar.dart';
import 'package:stad/constant/colors.dart';

class MyContentsScreen extends StatefulWidget {
  const MyContentsScreen({super.key});

  @override
  State<MyContentsScreen> createState() => _MyContentsScreenState();
}

class _MyContentsScreenState extends State<MyContentsScreen> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: CustomAppBar(
        title: '내가 본 콘텐츠',
        titleStyle: TextStyle(
            fontSize: 18, color: mainWhite, fontWeight: FontWeight.bold),
        showBackButton: true,
      ),
    );
  }
}
