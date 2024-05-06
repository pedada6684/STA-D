import 'package:flutter/material.dart';
import 'package:stad/constant/colors.dart';
import 'package:stad/models/cart_model.dart';
import 'package:stad/services/cart_service.dart';
import 'package:stad/widget/app_bar.dart';
import 'package:stad/widget/button.dart';
import 'package:stad/widget/quantity_changer.dart';

class CartScreen extends StatefulWidget {
  const CartScreen({super.key});

  @override
  State<CartScreen> createState() => _CartScreenState();
}

class _CartScreenState extends State<CartScreen> {
  late CartService cartService;
  late List<CartItem> cartItems = [];
  bool isSelectAll = false;

  @override
  void initState() {
    super.initState();
    cartService = CartService();
    cartItems = cartService.getDummyCartData();
    cartItems.forEach((item) {
      item.isSelected = !item.isSelected;
    });
  }

  void toggleItemSelection(int index) {
    setState(() {
      cartItems[index].isSelected = !cartItems[index].isSelected;
      isSelectAll = cartItems.every((item) => item.isSelected);
    });
  }

  void toggleSelectAll() {
    setState(() {
      isSelectAll = !isSelectAll;
      for (var item in cartItems) {
        item.isSelected = isSelectAll;
      }
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: backGray,
      appBar: CustomAppBar(
        title: '장바구니',
        titleStyle: TextStyle(
          color: mainNavy,
          fontWeight: FontWeight.bold,
          fontSize: 18.0,
        ),
      ),
      body: Column(
        children: [
          _selectAllContainer(),
          Expanded(
            child: cartItems.isNotEmpty ? _buildCartList() : _buildEmptyCart(),
          )
        ],
      ),
      bottomNavigationBar:
          cartItems.isNotEmpty ? _buildTotalPriceButton() : null,
    );
  }

  Widget _selectAllContainer() {
    return Container(
      padding: EdgeInsets.symmetric(horizontal: 16.0, vertical: 8.0),
      color: mainWhite,
      child: Row(
        children: [
          Row(
            children: [
              IconButton(
                icon: isSelectAll
                    ? Icon(
                        Icons.check_circle_rounded,
                        color: mainNavy,
                        size: 30.0,
                      )
                    : Icon(
                        Icons.check_circle_outline_rounded,
                        color: mainGray,
                        size: 30.0,
                      ),
                onPressed: toggleSelectAll,
              ),
              Text('전체 선택'),
            ],
          ),
        ],
      ),
    );
  }

  Widget _buildCartList() {
    return ListView.builder(
      itemCount: cartItems.length,
      itemBuilder: (context, index) {
        // index를 직접 전달
        return _buildCartItem(index);
      },
    );
  }

  Widget _buildCartItem(int index) {
    CartItem item = cartItems[index];
    return Container(
      margin: EdgeInsets.only(top: 10.0),
      color: mainWhite,
      padding: EdgeInsets.symmetric(vertical: 16.0, horizontal: 16.0),
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              Row(
                children: [
                  IconButton(
                    icon: Icon(
                      item.isSelected
                          ? Icons.check_circle_rounded
                          : Icons.check_circle_outline_rounded,
                      color: mainNavy,
                      size: 30.0,
                    ),
                    onPressed: () => toggleItemSelection(index),
                  ),
                  Text(
                    item.title,
                    style: TextStyle(fontSize: 14, fontWeight: FontWeight.w600),
                  ),
                ],
              ),
              IconButton(
                icon: Icon(
                  Icons.close_rounded,
                  color: midGray,
                ),
                onPressed: () {
                  setState(() {
                    cartItems.removeAt(index);
                    // 삭제 후 '전체 선택' 상태를 다시 확인합니다.
                    isSelectAll = cartItems.every((item) => item.isSelected);
                  });
                },
              ),
            ],
          ),
          Padding(
            padding: const EdgeInsets.symmetric(vertical: 4.0),
            child: Row(
              children: [
                SizedBox(width: 48.0),
                Image.asset(item.thumbnail, width: 70, height: 70),
                SizedBox(width: 16.0),
                Expanded(
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Text('${item.price}원', style: TextStyle(color: midGray)),
                      //TODO:수정하기 quantityChanger
                      // QuantityChanger(
                      //   initialQuantity: item.quantity,
                      //   maxQuantity: ,
                      //   onQuantityChanged: (newQuantity) {
                      //     setState(() {
                      //       cartItems[index].quantity = newQuantity;
                      //     });
                      //   },
                      // ),
                    ],
                  ),
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }

  Widget _buildTotalPriceButton() {
    final totalPrice = cartItems.fold(
        0,
        (previousValue, element) =>
            previousValue + (element.price * element.quantity));
    return CustomElevatedButton(
      onPressed: () {
        //TODO: 주문하기 로직 작성
      },
      text: '${totalPrice.toString()}원 주문하기',
      textColor: mainWhite,
      backgroundColor: mainNavy,
    );
  }

  Widget _buildEmptyCart() {
    return Center(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        crossAxisAlignment: CrossAxisAlignment.center,
        children: [
          Spacer(),
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
          SizedBox(height: 10.0),
        ],
      ),
    );
  }
}
