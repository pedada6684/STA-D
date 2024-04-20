import 'dart:ui';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:marquee/marquee.dart';
import 'package:stad/constant/colors.dart';
import 'package:stad/screen/product/product_screen.dart';

class HomeScreen extends StatefulWidget {
  const HomeScreen({super.key});

  @override
  State<HomeScreen> createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> with WidgetsBindingObserver {
  bool _isActive = true;

  @override
  void initState() {
    super.initState();
    WidgetsBinding.instance.addObserver(this);
  }

  @override
  void dispose() {
    WidgetsBinding.instance.removeObserver(this);
    super.dispose();
  }

  @override
  void didChangeAppLifecycleState(AppLifecycleState state) {
    if (state == AppLifecycleState.resumed) {
      // 앱이 다시 활성화되었을 때
      setState(() {
        _isActive = true;
      });
    } else if (state == AppLifecycleState.paused) {
      // 앱이 비활성화되었을 때
      setState(() {
        _isActive = false;
      });
    }
  }

  Route _createRoute() {
    return PageRouteBuilder(
      transitionDuration: Duration(milliseconds: 250),
      reverseTransitionDuration: Duration(milliseconds: 250),
      pageBuilder: (context, animation, secondaryAnimation) => ProductScreen(),
      transitionsBuilder: (context, animation, secondaryAnimation, child) {
        var begin = Offset(1.0, 0.0); // 오른쪽
        var end = Offset.zero;
        var curve = Curves.ease;

        var tween =
            Tween(begin: begin, end: end).chain(CurveTween(curve: curve));
        var offsetAnimation = animation.drive(tween);

        return SlideTransition(
          position: offsetAnimation,
          child: child,
        );
      },
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: mainWhite,
        centerTitle: true,
        title: Text(
          'STA:D',
          style: TextStyle(
              color: mainNavy, fontFamily: 'LogoFont', fontSize: 40.0),
        ),
      ),
      body: SingleChildScrollView(
        child: Column(
          children: [
            Container(
              height: 48.0,
              width: MediaQuery.of(context).size.width,
              color: mainNavy,
              child: _isActive
                  ? Marquee(
                      text: '💫 BEYOND THE SCREEN! 💫                 ',
                      style: TextStyle(
                          color: mainWhite,
                          fontSize: 18.0,
                          fontWeight: FontWeight.bold),
                      scrollAxis: Axis.horizontal,
                      crossAxisAlignment: CrossAxisAlignment.center,
                      blankSpace: 20.0,
                      velocity: 50.0,
                      startPadding: 10.0,
                      accelerationDuration: Duration.zero,
                      accelerationCurve: Curves.linear,
                      decelerationDuration: Duration.zero,
                      decelerationCurve: Curves.easeOut,
                    )
                  : Container(),
            ),
            Padding(
              padding:
                  const EdgeInsets.symmetric(vertical: 16.0, horizontal: 4.0),
              child: Stack(
                alignment: Alignment.center,
                children: [
                  ClipRRect(
                    borderRadius: BorderRadius.circular(10.0),
                    child: Image.asset('assets/image/thumbnail.png'),
                  ),
                  Positioned(
                    top: 20,
                    left: 15,
                    child: Container(
                      padding: EdgeInsets.all(8.0),
                      child: Text(
                        '지금 보는 콘텐츠',
                        style: TextStyle(
                          color: mainWhite,
                          fontSize: 22.0,
                          fontWeight: FontWeight.bold,
                        ),
                      ),
                    ),
                  ),
                ],
              ),
            ),
            Padding(
              padding:
                  const EdgeInsets.symmetric(vertical: 16.0, horizontal: 4.0),
              child: Stack(
                alignment: Alignment.center,
                children: [
                  ClipRRect(
                    borderRadius: BorderRadius.circular(10.0),
                    child: Container(
                      height: 180, // 이미지 높이 지정
                      child: Stack(
                        fit: StackFit.expand,
                        children: [
                          Image.asset(
                            'assets/image/advertising.png',
                          ),
                          BackdropFilter(
                            filter: ImageFilter.blur(sigmaX: 3.0, sigmaY: 3.0),
                            child: Container(
                              color: mainBlack.withOpacity(0.2),
                            ),
                          ),
                          Positioned(
                            top: 20,
                            left: 15,
                            child: Container(
                              padding: EdgeInsets.all(8.0),
                              child: Text(
                                '지금 보는 광고 구매하기',
                                style: TextStyle(
                                  color: mainWhite,
                                  fontSize: 22.0,
                                  fontWeight: FontWeight.bold,
                                ),
                              ),
                            ),
                          ),
                          Positioned(
                            right: 15,
                            bottom: 15,
                            child: IconButton(
                              icon: Icon(
                                Icons.ads_click_rounded,
                                color: mainWhite,
                                size: 32.0,
                              ),
                              onPressed: () {
                                Navigator.of(context).push(_createRoute());
                              },
                            ),
                          )
                        ],
                      ),
                    ),
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}
