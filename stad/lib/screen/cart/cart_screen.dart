import 'package:flutter/material.dart';
import 'package:stad/component/app_bar.dart';
import 'package:stad/component/button.dart';
import 'package:stad/constant/colors.dart';

class CartScreen extends StatefulWidget {
  const CartScreen({super.key});

  @override
  State<CartScreen> createState() => _CartScreenState();
}

class _CartScreenState extends State<CartScreen> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: CustomAppBar(
        title: '장바구니',
        titleStyle: TextStyle(
          color: mainWhite,
          fontWeight: FontWeight.bold,
          fontSize: 18.0,
        ),
      ),
      body:
      _buildEmptyCart(),
    );
  }
}

class _buildEmptyCart extends StatelessWidget {
  const _buildEmptyCart({
    super.key,
  });

  @override
  Widget build(BuildContext context) {
    return Center(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        crossAxisAlignment: CrossAxisAlignment.center,
        children: [
          Spacer(),
          // This will take all available space, pushing the rest to the center
          Text(
            '장바구니에 담긴 상품이 없습니다.',
            style: TextStyle(fontSize: 16.0, color: darkGray),
            textAlign: TextAlign.center,
          ),
          Spacer(),
          CustomElevatedButton(
              text: '상품을 담아주세요.',
              onPressed: null,
              textColor: mainWhite,
              backgroundColor: mainGray),
          SizedBox(
            height: 10.0,
          ),
          // Use another spacer to ensure everything is centered
        ],
      ),
    );
  }
}
