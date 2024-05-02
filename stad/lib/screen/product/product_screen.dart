import 'package:flutter/material.dart';
import 'package:stad/constant/colors.dart';
import 'package:stad/models/product_model.dart';
import 'package:stad/screen/product/option_bottom_sheet.dart';
import 'package:stad/screen/product/product_detail_screen.dart';
import 'package:stad/screen/review/review_screen.dart';
import 'package:stad/services/product_service.dart';
import 'package:stad/widget/app_bar.dart';
import 'package:stad/widget/button.dart';

class ProductScreen extends StatefulWidget {
  final int advertId;
  final String title;
  final String description;

  const ProductScreen({super.key, required this.advertId, required this.title, required this.description});

  @override
  State<ProductScreen> createState() => _ProductScreenState();
}

class _ProductScreenState extends State<ProductScreen>
    with SingleTickerProviderStateMixin {
  late TabController _tabController;
  late ProductService _productService;
  ProductInfo? _productInfo;
  List<ProductType> _productTypes = [];

  @override
  void initState() {
    super.initState();
    _tabController = TabController(length: 2, vsync: this);
    _productService = ProductService();
    _loadProductData();
  }

  void _loadProductData() async {
    try {
      _productInfo = await _productService.getProductInfo(widget.advertId);
      _productTypes = await _productService.getProductTypeList(_productInfo!.id);
      if (_tabController == null || _tabController.length != 2) {
        _tabController = TabController(length: 2, vsync: this);
      }
      setState(() {}); // 데이터가 로드된 후 UI를 업데이트
    } catch (e) {
      print('Error loading product data: $e');
    }
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
        title: _productInfo != null && _productTypes.isNotEmpty
            ? _productTypes[0].name
            : '상품 상세',
        showBackButton: true,
        tabController: _tabController,
      ),
      body: TabBarView(
        controller: _tabController,
        children: [
          _productInfo != null ?
          ProductDetailScreen(productInfo: _productInfo, productTypes: _productTypes, title: widget.title, description:widget.description ) :
          Center(child: Text('상품 정보가 없습니다')),
          Center(child: ReviewScreen()),
        ],
      ),
      bottomNavigationBar: CustomElevatedButton(
        text: '주문하기',
        textColor: mainWhite,
        backgroundColor: mainNavy,
        onPressed: () {
          if (_productTypes.isNotEmpty) {
            showProductOptionBottomSheet(context, _productInfo, _productTypes, widget.title);
          }
        },
      ),
    );
  }
}
