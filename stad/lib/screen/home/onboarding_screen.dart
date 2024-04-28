import 'package:flutter/material.dart';
import 'package:introduction_screen/introduction_screen.dart';
import 'package:stad/constant/colors.dart';
import 'package:stad/screen/home/home_screen.dart';

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
      autoScrollDuration: 3000,
      pages: [
        PageViewModel(
          title: 'advertising',
          image: Image.asset('assets/image/ad_mockup.png'),
          body: '사용자 맞춤형 광고와 콘텐츠 맞춤형 광고',
          decoration: getPageDecoration(),
        ),
        PageViewModel(
          title: 'get',
          body: '광고에 나오는 제품 바로 구매하기',
          decoration: getPageDecoration(),
        ),
      ],
      showBackButton: true,
      showNextButton: true,
      // showDoneButton: true,
      next: Icon(
        Icons.arrow_forward_ios_rounded,
        size: 20.0,
      ),
      back: Icon(
        Icons.arrow_back_ios_rounded,
        size: 20.0,
      ),
      onDone: () {
        Navigator.of(context).pushReplacement(
            MaterialPageRoute(builder: (context) => HomeScreen()));
      },
      done: Text(
        'STA:D',
        style: TextStyle(
            color: mainNavy, fontSize: 18.0, fontWeight: FontWeight.bold),
      ),
      curve: Curves.ease,
    );
  }

  PageDecoration getPageDecoration() {
    return const PageDecoration(
      titleTextStyle: TextStyle(
          fontSize: 44,
          fontWeight: FontWeight.bold,
          fontFamily: 'LogoFont',
          color: mainNavy),
      bodyTextStyle: TextStyle(
        fontSize: 16,
        color: mainNavy,
      ),
      imagePadding: EdgeInsets.only(top: 40),
      pageColor: mainWhite,
    );
  }
}
