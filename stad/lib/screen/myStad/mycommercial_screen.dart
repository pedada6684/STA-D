import 'package:flutter/material.dart';
import 'package:stad/constant/colors.dart';
import 'package:stad/widget/app_bar.dart';

class MyCommercialScreen extends StatefulWidget {
  const MyCommercialScreen({super.key});

  @override
  State<MyCommercialScreen> createState() => _MyCommercialScreenState();
}

class _MyCommercialScreenState extends State<MyCommercialScreen> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: CustomAppBar(
        title: '내가 본 광고',
        titleStyle: TextStyle(
            fontSize: 18, color: mainWhite, fontWeight: FontWeight.bold),
        showBackButton: true,
      ),
    );
  }
}
