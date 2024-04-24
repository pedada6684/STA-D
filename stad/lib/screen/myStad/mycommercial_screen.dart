import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:stad/constant/colors.dart';
import 'package:stad/screen/product/product_screen.dart';
import 'package:stad/widget/advertising_card.dart';
import 'package:stad/widget/app_bar.dart';
import 'package:stad/widget/mycommercial_container.dart';

class MyCommercialScreen extends StatefulWidget {
  const MyCommercialScreen({super.key});

  @override
  State<MyCommercialScreen> createState() => _MyCommercialScreenState();
}

class _MyCommercialScreenState extends State<MyCommercialScreen> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      // backgroundColor: mainNavy.withOpacity(0.9),
      appBar: CustomAppBar(
        title: '내가 본 광고',
        showBackButton: true,
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          children: [
            Padding(
              padding: const EdgeInsets.symmetric(horizontal: 4.0),
              child: Row(
                mainAxisAlignment: MainAxisAlignment.start,
                children: [
                  Text(
                    '민형이가 조아하는 딸기',
                    style:
                        TextStyle(fontSize: 18.0, fontWeight: FontWeight.bold),
                  ),
                ],
              ),
            ),
            AdvertisingCard(
                imagePath: 'assets/image/advertising.png',
                buttonText: '',
                onPressed: () {
                  Navigator.push(
                    context,
                    CupertinoPageRoute(
                      builder: (context) => ProductScreen(),
                    ),
                  );
                }),
          ],
        ),
      ),
    );
  }
}
