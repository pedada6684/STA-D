//나중에 서버에서 받아올 것
import 'dart:ui';

import 'package:carousel_slider/carousel_slider.dart';
import 'package:flutter/material.dart';
import 'package:stad/constant/colors.dart';
import 'package:stad/screen/product/product_screen.dart';
import 'package:stad/widget/advertising_card.dart';

class HomeScreen extends StatefulWidget {
  const HomeScreen({super.key});

  @override
  State<HomeScreen> createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> with WidgetsBindingObserver {
  bool _isActive = true;
  int _current = 0;
  final CarouselController _controller = CarouselController();

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

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: PreferredSize(
        preferredSize: Size.fromHeight(50.0), // 앱바 높이 설정
        child: AppBar(
          scrolledUnderElevation: 0,
          backgroundColor: Colors.transparent,
          // AppBar 배경을 투명하게 설정
          elevation: 0,
          // 그림자 제거
          title: Text(
            'STA:D',
            style: TextStyle(
              color: mainWhite,
              fontSize: 35,
              fontWeight: FontWeight.bold,
              fontFamily: 'LogoFont',
              shadows: <Shadow>[
                Shadow(
                  offset: Offset(0.0, 1.0),
                  blurRadius: 3.0,
                  color: Color.fromARGB(150, 0, 0, 0),
                ),
              ],
            ),
          ),
          centerTitle: true,
        ),
      ),
      extendBodyBehindAppBar: true, // 바디를 앱바 뒤로 확장
      body: SingleChildScrollView(
        child: Column(
          children: [
            Stack(
              children: [
                ShaderMask(
                  shaderCallback: (rect) {
                    return LinearGradient(
                      begin: Alignment.topCenter,
                      end: Alignment.bottomCenter,
                      colors: [Colors.black, Colors.transparent],
                      stops: [0.5, 1.0], // 상단 절반을 덮는 그라데이션
                    ).createShader(
                        Rect.fromLTRB(0, 0, rect.width, rect.height));
                  },
                  blendMode: BlendMode.dstIn,
                  child: Image.asset(
                    'assets/image/thumbnail5.jpg', // 서버에서 받아올 이미지 URL
                    height: 420,
                    width: double.infinity,
                    fit: BoxFit.cover,
                  ),
                ),
                Positioned(
                  top: 0,
                  left: 0,
                  right: 0,
                  child: Container(
                    height: 100, // 이미지의 높이와 동일하게 설정
                    decoration: BoxDecoration(
                        gradient: LinearGradient(
                            begin: Alignment.topCenter,
                            end: Alignment.bottomCenter,
                            colors: [
                          mainBlack.withOpacity(0.2),
                          Colors.transparent,
                        ],
                            stops: [
                          0.0,
                          0.5
                        ])),
                  ),
                ),
                Positioned(
                  bottom: 35,
                  left: 25,
                  child: Text(
                    '지금 보는 컨텐츠',
                    style: TextStyle(
                      fontSize: 18,
                      fontWeight: FontWeight.bold,
                      color: mainWhite,
                      shadows: <Shadow>[
                        Shadow(
                          offset: Offset(0.0, 0.8),
                          blurRadius: 3.0,
                          color: Color.fromARGB(150, 0, 0, 0),
                        ),
                      ],
                    ),
                  ),
                ),
              ],
            ),
            Column(
              children: [
                AdvertisingCard(
                  imagePath: 'assets/image/product3.jfif',
                  buttonText: '지금 보는 광고가 궁금하다면?',
                  onPressed: () {
                    Navigator.of(context).push(MaterialPageRoute(
                        builder: (context) => ProductScreen()));
                  },
                ),
                CarouselSlider(
                  items: [
                    AdvertisingCard(
                      imagePath: 'assets/image/product.png',
                      buttonText: '지금 보는 광고가 궁금하다면?',
                      onPressed: () {
                        Navigator.of(context).push(MaterialPageRoute(
                            builder: (context) => ProductScreen()));
                      },
                    ),
                    AdvertisingCard(
                      imagePath: 'assets/image/product2.png',
                      buttonText: '콘텐츠 관련 광고 구매하기',
                      onPressed: () {
                        Navigator.of(context).push(MaterialPageRoute(
                            builder: (context) => ProductScreen()));
                      },
                    ),
                  ],
                  options: CarouselOptions(
                      autoPlay: true,
                      autoPlayInterval: Duration(seconds: 3),
                      autoPlayAnimationDuration: Duration(milliseconds: 800),
                      enableInfiniteScroll: true,
                      aspectRatio: 16 / 9,
                      viewportFraction: 1,
                      enlargeCenterPage: true,
                      scrollDirection: Axis.horizontal,
                      onPageChanged: (index, reason) {
                        setState(() {
                          _current = index;
                        });
                      }),
                  carouselController: _controller,
                ),
              ],
            ),
          ],
        ),
      ),
    );
  }
}
