import 'package:flutter/material.dart';
import 'package:onboarding_animation/onboarding_animation.dart';
import 'package:stad/constant/colors.dart';
import 'package:go_router/go_router.dart';

class OnboardingScreen extends StatefulWidget {
  const OnboardingScreen({super.key});

  @override
  State<OnboardingScreen> createState() => _OnboardingScreenState();
}

class _OnboardingScreenState extends State<OnboardingScreen> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.white.withOpacity(.9),
      body: OnBoardingAnimation(
        controller: PageController(initialPage: 0),
        pages: [
          _GetCardsContent(
            image: 'assets/image/ad_mockup.png',
            title: 'advertising',
            cardContent: '사용자 맞춤형 광고와 콘텐츠 맞춤형 광고!',
            button: Padding(
              padding: const EdgeInsets.only(top: 10.0),
              child: TextButton(
                onPressed: () => context.go('/login'),
                child: Text('STA:D 로그인하기',
                    style: TextStyle(
                        color: mainNavy,
                        fontFamily: 'LogoFont',
                        fontSize: 18.0)),
              ),
            ),
          ),
          _GetCardsContent(
            image: 'assets/image/ad_mockup.png',
            title: 'get',
            cardContent: '시청과 쇼핑을 하나로!',
            button: Padding(
              padding: const EdgeInsets.only(top: 10.0),
              child: TextButton(
                onPressed: () => context.go('/login'),
                child: Text('STA:D 로그인하기',
                    style: TextStyle(
                        color: mainNavy,
                        fontFamily: 'LogoFont',
                        fontSize: 18.0)),
              ),
            ),
          ),
        ],
        indicatorDotHeight: 7.0,
        indicatorDotWidth: 7.0,
        indicatorType: IndicatorType.expandingDots,
        indicatorPosition: IndicatorPosition.bottomCenter,
        indicatorSwapType: SwapType.normal,
        // onDone: () => context.go('/login'),
        // done: const Text("STA:D", style: TextStyle(fontWeight: FontWeight.bold)),
      ),
    );
  }
}

class _GetCardsContent extends StatelessWidget {
  final String image, title, cardContent;
  final Widget? button;

  const _GetCardsContent({
    Key? key,
    this.image = '',
    this.title = '',
    this.cardContent = '',
    this.button,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      decoration: const BoxDecoration(
        color: Colors.white,
        borderRadius: BorderRadius.all(Radius.circular(20.0)),
      ),
      child: Padding(
        padding: const EdgeInsets.all(11.0),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          children: [
            Text(
              title,
              style: TextStyle(
                fontSize: 44,
                fontWeight: FontWeight.bold,
                color: mainNavy,
                fontFamily: 'LogoFont',
              ),
            ),
            ClipRRect(
              borderRadius: const BorderRadius.all(Radius.circular(20.0)),
              child: Image.asset(image, width: 350),
            ),
            Column(
              children: [
                Text(
                  cardContent,
                  textAlign: TextAlign.center,
                  style: TextStyle(
                      fontSize: 18,
                      color: mainNavy,
                      fontWeight: FontWeight.w600),
                ),
                if (button != null) button!,
              ],
            ),
          ],
        ),
      ),
    );
  }
}
