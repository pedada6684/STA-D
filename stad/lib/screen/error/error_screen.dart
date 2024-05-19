import 'package:flutter/material.dart';
import 'package:stad/constant/colors.dart';

class ErrorScreen extends StatelessWidget {
  final String error;

  const ErrorScreen({super.key, required this.error});

  @override
  Widget build(BuildContext context) {
    return Center(
      child: Column(
        children: [
          Text(
            '힝 에러남',
            style: TextStyle(
                fontFamily: 'LogoFont', fontSize: 32.0, color: midGray),
          ),
          Text(error),
        ],
      ),
    );
  }
}
