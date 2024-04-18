import 'package:flutter/material.dart';
import 'package:stad/component/app_bar.dart';
import 'package:stad/constant/colors.dart';

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
        titleStyle: TextStyle(
            fontSize: 18, color: mainWhite, fontWeight: FontWeight.bold),
        showBackButton: true,
        tabController: _tabController,
      ),
      body: TabBarView(
        controller: _tabController,
        children: [
          Center(child: Text('상품 상세 내용')),
          Center(child: Text('리뷰 내용')),
        ],
      ),
    );
  }
}
