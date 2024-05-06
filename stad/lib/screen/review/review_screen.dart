import 'package:flutter/material.dart';
import 'package:stad/constant/colors.dart';

class ReviewScreen extends StatelessWidget {
  const ReviewScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: mainWhite,
      body: Center(child: Text('상품 관련 후기가 없습니다.')),
    );
  }
}
