import 'package:flutter/material.dart';
import 'package:stad/constant/colors.dart';

class LoginScreen extends StatelessWidget {
  const LoginScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Column(
        crossAxisAlignment: CrossAxisAlignment.center,
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Center(
            child: Text(
              'STA:D',
              style: TextStyle(
                  fontFamily: 'LogoFont', fontSize: 80.0, color: mainNavy),
            ),
          ),
        ],
      ),
    );
  }
}
