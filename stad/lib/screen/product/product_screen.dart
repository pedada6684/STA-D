import 'package:flutter/material.dart';
import 'package:stad/component/app_bar.dart';
import 'package:stad/constant/colors.dart';

class ProductScreen extends StatefulWidget {
  const ProductScreen({super.key});

  @override
  State<ProductScreen> createState() => _ProductScreenState();
}

class _ProductScreenState extends State<ProductScreen> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: CustomAppBar(
        title: '상품명임',
        titleStyle: TextStyle(
            fontSize: 18, color: mainWhite, fontWeight: FontWeight.bold),
        showBackButton: true,
      ),
    );
  }
}
