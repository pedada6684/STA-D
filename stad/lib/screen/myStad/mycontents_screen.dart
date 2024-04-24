import 'package:flutter/material.dart';
import 'package:stad/constant/colors.dart';
import 'package:flutter_carousel_widget/flutter_carousel_widget.dart';
import 'package:stad/widget/app_bar.dart';

class MyContentsScreen extends StatefulWidget {
  const MyContentsScreen({super.key});

  @override
  State<MyContentsScreen> createState() => _MyContentsScreenState();
}

class _MyContentsScreenState extends State<MyContentsScreen> {
  final List<String> imageList = [
    'assets/image/1.png',
    'assets/image/2.png',
    'assets/image/3.png',
    'assets/image/4.png',
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: CustomAppBar(
        title: '내가 본 콘텐츠',
        showBackButton: true,
      ),
      body: Center(
        child: FlutterCarousel(
          options: CarouselOptions(
            autoPlay: true,
            autoPlayInterval: const Duration(seconds: 3),
            enlargeCenterPage: true,
            // 중앙 이미지 확대 옵션 활성화
            height: MediaQuery.of(context).size.height * 0.3,
            viewportFraction: 0.8,
            indicatorMargin: 12.0,
            enableInfiniteScroll: true,
            slideIndicator: CircularSlideIndicator(), // 페이지 인디케이터 사용
          ),
          items: imageList.map((item) {
            return Builder(
              builder: (BuildContext context) {
                return Container(
                  width: MediaQuery.of(context).size.width,
                  margin: EdgeInsets.symmetric(horizontal: 5.0),
                  decoration: BoxDecoration(
                    image: DecorationImage(
                      image: AssetImage(item),
                      fit: BoxFit.cover,
                    ),
                  ),
                  child: Center(
                    child: Text(
                      'Image $item',
                      style: TextStyle(
                        fontSize: 16.0,
                        fontWeight: FontWeight.bold,
                        color: Colors.white,
                      ),
                    ),
                  ),
                );
              },
            );
          }).toList(),
        ),
      ),
    );
  }
}
