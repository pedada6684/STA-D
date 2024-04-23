import 'package:flutter/material.dart';
import 'package:stad/constant/colors.dart';
import 'package:stad/screen/product/option_bottom_sheet.dart';
import 'package:stad/screen/product/product_detail_screen.dart';
import 'package:stad/widget/app_bar.dart';
import 'package:stad/widget/button.dart';

class ProductScreen extends StatefulWidget {
  const ProductScreen({super.key});

  @override
  State<ProductScreen> createState() => _ProductScreenState();
}

class _ProductScreenState extends State<ProductScreen>
    with SingleTickerProviderStateMixin {
  late TabController _tabController;

  @override
  void initState() {
    super.initState();
    _tabController = TabController(length: 2, vsync: this);
  }

  @override
  void dispose() {
    _tabController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: CustomAppBar(
        title: '상품명임',
        titleStyle: const TextStyle(
            fontSize: 20, color: mainNavy, fontWeight: FontWeight.bold),
        showBackButton: true,
        tabController: _tabController,
      ),
      body: TabBarView(
        controller: _tabController,
        children: const [
          ProductDetailScreen(),
          Center(child: Text('리뷰 내용')),
        ],
      ),
      bottomNavigationBar: SafeArea(
        child: Padding(
          padding: const EdgeInsets.all(16.0),
          child: CustomElevatedButton(
            text: '주문하기',
            textColor: mainWhite,
            backgroundColor: mainNavy,
            onPressed: () {
              showProductOptionBottomSheet(context);
            },
          ),
        ),
      ),
    );
  }
}
