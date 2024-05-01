import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import 'package:introduction_screen/introduction_screen.dart';
import 'package:stad/constant/colors.dart';
import 'package:stad/screen/home/home_screen.dart';
import 'package:stad/screen/login/login_screen.dart';

class OnboardingScreen extends StatefulWidget {
  const OnboardingScreen({super.key});

  @override
  State<OnboardingScreen> createState() => _OnboardingScreenState();
}

class _OnboardingScreenState extends State<OnboardingScreen> {
  final introKey = GlobalKey<IntroductionScreenState>();

  @override
  Widget build(BuildContext context) {
    return IntroductionScreen(
      key: introKey,
      pages: [
        PageViewModel(
          titleWidget: Column(
            children: [
              SizedBox(
                height: 60.0,
              ),
              Text(
                'advertising',
                style: TextStyle(
                  fontSize: 44,
                  fontWeight: FontWeight.bold,
                  color: mainNavy,
                  fontFamily: 'LogoFont',
                ),
              ),
            ],
          ),
          bodyWidget: Column(
            children: [
              SizedBox(height: 60),
              Image.asset('assets/image/ad_mockup.png', width: 350),
              SizedBox(height: 40),
              Text(
                '사용자 맞춤형 광고와 콘텐츠 맞춤형 광고',
                textAlign: TextAlign.center,
                style: TextStyle(
                    fontSize: 18, color: mainNavy, fontWeight: FontWeight.w600),
              ),
            ],
          ),
          decoration: PageDecoration(
            pageColor: mainWhite,
          ),
        ),
        PageViewModel(
          titleWidget: Column(
            children: [
              SizedBox(height: 60),
              Text(
                'get',
                style: TextStyle(
                  fontSize: 44,
                  fontWeight: FontWeight.bold,
                  color: mainNavy,
                  fontFamily: 'LogoFont',
                ),
              ),
            ],
          ),
          bodyWidget: Column(
            children: [
              SizedBox(height: 60),
              Image.asset('assets/image/ad_mockup.png', width: 350),
              SizedBox(height: 40),
              Text(
                '광고 중인 상품이 궁금하다면?',
                textAlign: TextAlign.center,
                style: TextStyle(
                    fontSize: 18, color: mainNavy, fontWeight: FontWeight.w600),
              ),
            ],
          ),
          decoration: PageDecoration(
            pageColor: mainWhite,
          ),
        ),
      ],
      showNextButton: true,
      showBackButton: true,
      next: Icon(Icons.arrow_forward_ios_rounded, size: 20.0),
      back: Icon(Icons.arrow_back_ios_rounded, size: 20.0),
      // onDone: () => Builder(
      //   builder: (context) {
      //     context.go('/login');
      //     return SizedBox.shrink();
      //   },
      // ),
      // onDone: () {
      //   Navigator.of(context).pushReplacement(
      //       MaterialPageRoute(builder: (context) => LoginScreen()));
      // },
      onDone: () => context.go('/login'),
      done: Text(
        'STA:D',
        style: TextStyle(
            color: mainNavy,
            fontSize: 18.0,
            fontWeight: FontWeight.bold,
            fontFamily: 'LogoFont'),
      ),
      globalBackgroundColor: mainWhite,
      curve: Curves.ease,
    );
  }
}
