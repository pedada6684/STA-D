import 'package:flutter/material.dart';
import 'package:google_sign_in/google_sign_in.dart';
import 'package:stad/constant/colors.dart';

class LoginScreen extends StatefulWidget {
  const LoginScreen({super.key});

  @override
  State<LoginScreen> createState() => _LoginScreenState();
}

class _LoginScreenState extends State<LoginScreen> {
  void signInWithGoogle(BuildContext context) async {
    final GoogleSignInAccount? googleUser = await GoogleSignIn().signIn();

    if (googleUser != null) {
      //로그인 상태
      print(googleUser);
    }
  }

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
          SizedBox(height: 50), // 간격 조절
          ElevatedButton(
            onPressed: () => signInWithGoogle(context),
            style: ElevatedButton.styleFrom(backgroundColor: mainNavy),
            child: Text(
              'Google 로그인',
              style: TextStyle(color: mainWhite),
            ),
          ),
        ],
      ),
    );
  }
}
