import 'dart:ui';

import 'package:flutter/material.dart';
import 'package:stad/constant/colors.dart';

class AdvertisingCard extends StatelessWidget {
  final String bannerImgUrl;
  final String buttonText;
  final String subText;
  final VoidCallback onPressed;

  const AdvertisingCard({
    Key? key,
    required this.bannerImgUrl,
    required this.buttonText,
    required this.subText,
    required this.onPressed,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 4.0, horizontal: 4.0),
      child: InkWell(
        onTap: onPressed, // InkWell 사용하여 전체 이미지 탭 가능하게 함
        child: Stack(
          alignment: Alignment.center,
          children: [
            ClipRRect(
              borderRadius: BorderRadius.circular(10.0),
              child: Container(
                height: 200, // 이미지 높이 지정
                child: Stack(
                  fit: StackFit.expand,
                  children: [
                    Image.network(bannerImgUrl, fit: BoxFit.cover),
                    // BackdropFilter(
                    //   filter: ImageFilter.blur(sigmaX: 2.0, sigmaY: 2.0),
                    //   child: Container(
                    //     color: Colors.black.withOpacity(0.1),
                    //   ),
                    // ),
                    Positioned(
                      bottom: 10,
                      left: 15,
                      child: Container(
                        padding: const EdgeInsets.all(8.0),
                        child: Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            Text(
                              buttonText,
                              style: TextStyle(
                                color: mainWhite,
                                fontSize: 16.0,
                                fontWeight: FontWeight.bold,
                                shadows: <Shadow>[
                                  Shadow(
                                    offset: Offset(0.0, 0.8),
                                    blurRadius: 3.0,
                                    color: Color.fromARGB(150, 0, 0, 0),
                                  ),
                                ],
                              ),
                            ),
                            Text(
                              subText,
                              style: TextStyle(
                                color: mainWhite,
                                fontSize: 12.0,

                                shadows: <Shadow>[
                                  Shadow(
                                    offset: Offset(0.0, 0.8),
                                    blurRadius: 3.0,
                                    color: Color.fromARGB(150, 0, 0, 0),
                                  ),
                                ],
                              ),
                            )
                          ],
                        ),
                      ),
                    ),
                  ],
                ),
              ),
            ),
            SizedBox(height: 20.0,)
          ],
        ),
      ),
    );
  }
}
