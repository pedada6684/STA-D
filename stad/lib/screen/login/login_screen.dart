import 'package:flutter/material.dart';
import 'package:stad/constant/colors.dart';
import 'package:stad/services/user_service.dart';

class LoginScreen extends StatefulWidget {
  const LoginScreen({super.key});

  @override
  State<LoginScreen> createState() => _LoginScreenState();
}

class _LoginScreenState extends State<LoginScreen> {
  final UserService _userService = UserService();

  void _handleSignIn() async {
    await _userService.signInWithGoogle(context);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: mainWhite,
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
          SizedBox(height: 50),
          ElevatedButton(
            onPressed: () => _handleSignIn(),
            style: ElevatedButton.styleFrom(
              backgroundColor: mainWhite, // 버튼 배경 색상
              padding: EdgeInsets.all(8.0),
              shape: RoundedRectangleBorder(
                borderRadius: BorderRadius.circular(10),
              ),
              elevation: 0,
            ),
            child: Container(
              decoration: BoxDecoration(
                border: Border.all(color: midGray),
                borderRadius: BorderRadius.all(
                  Radius.circular(5),
                ),
              ),
              padding: EdgeInsets.symmetric(horizontal: 8, vertical: 8),
              // 컨테이너 내부 여백
              child: IntrinsicHeight(
                child: Row(
                  mainAxisSize: MainAxisSize.min,
                  children: <Widget>[
                    Image.asset('assets/image/google.png', height: 24.0),
                    // Google 로고
                    SizedBox(width: 24),
                    // 로고와 텍스트 사이의 여백
                    Text(
                      'Google 계정으로 로그인',
                      style: TextStyle(
                        fontSize: 14.0, // 텍스트 크기 설정
                        fontWeight: FontWeight.w500, // 중간 두께
                        color: Colors.black.withOpacity(0.54), // 텍스트 색상 및 투명도
                      ),
                    ),
                    // 텍스트 끝 여백
                  ],
                ),
              ),
            ),
          ),
        ],
      ),
    );
  }
}
