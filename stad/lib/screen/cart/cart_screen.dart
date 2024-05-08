import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:stad/constant/colors.dart';
import 'package:stad/models/cart_model.dart';
import 'package:stad/providers/cart_provider.dart';
import 'package:stad/providers/user_provider.dart';
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
    WidgetsBinding.instance.addPostFrameCallback((_) => _loadCartItems());
  }

  void _loadCartItems() async {
    final userId = Provider.of<UserProvider>(context, listen: false).userId;
    List<CartItem> items = await cartService.fetchCartProducts(userId!);
    Provider.of<CartProvider>(context, listen: false).setCartItems(items);
  }

  // void toggleItemSelection(int index) {
  //   setState(() {
  //     cartItems[index].isSelected = !cartItems[index].isSelected;
  //     isSelectAll = cartItems.every((item) => item.isSelected);
  //   });
  // }
  //
  // void toggleSelectAll() {
  //   setState(() {
  //     isSelectAll = !isSelectAll;
  //     for (var item in cartItems) {
  //       item.isSelected = isSelectAll;
  //     }
  //   });
  // }

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
            child:
                Consumer<CartProvider>(builder: (context, cartProvider, child) {
              return StreamBuilder<List<CartItem>>(
                stream: cartProvider.cartItemsStream,
                builder: (context, snapshot) {
                  if (snapshot.connectionState == ConnectionState.waiting) {
                    return Center(child: CircularProgressIndicator());
                  }
                  if (!snapshot.hasData || snapshot.data!.isEmpty) {
                    return _buildEmptyCart();
                  }
                  return ListView.builder(
                    itemCount: snapshot.data!.length,
                    itemBuilder: (context, index) {
                      return _buildCartItem(snapshot.data![index], index);
                    },
                  );
                },
              );
            }),
          ),
        ],
      ),
      // bottomNavigationBar: cartItems.isNotEmpty
      //     ? Consumer<CartProvider>(builder: (context, cartProvider, child) {
      //         return _buildTotalPriceButton(cartProvider.getTotalPrice());
      //       })
      //     : SizedBox.shrink(),
      bottomNavigationBar: Consumer<CartProvider>(
        builder: (context, cartProvider, child) {
          return _buildTotalPriceButton(cartProvider.getTotalPrice());
        },
      ),
    );
  }

  Widget _selectAllContainer() {
    return Consumer<CartProvider>(
      builder: (context, cartProvider, child) => Container(
        padding: EdgeInsets.symmetric(horizontal: 16.0, vertical: 8.0),
        color: mainWhite,
        child: Row(
          // mainAxisAlignment: MainAxisAlignment.spaceBetween,
          children: [
            IconButton(
              icon: Icon(
                  cartProvider.isSelectAll
                      ? Icons.check_circle_rounded
                      : Icons.check_circle_outline_rounded,
                  color: cartProvider.isSelectAll ? mainNavy : mainGray,
                  size: 30.0),
              onPressed: () => cartProvider.toggleSelectAll(),
            ),
            Expanded(
              child: Text(
                  '전체 선택 (${cartProvider.cartItems.where((i) => i.isSelected).length}/${cartProvider.cartItems.length})'),
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildCartList() {
    return Consumer<CartProvider>(
      builder: (context, cartProvider, child) => ListView.builder(
        itemCount: cartProvider.cartItems.length,
        itemBuilder: (context, index) =>
            _buildCartItem(cartProvider.cartItems[index], index),
      ),
    );
  }

  Widget _buildCartItem(CartItem item, int index) {
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
              Expanded(
                child: Row(
                  children: [
                    IconButton(
                      icon: Icon(
                          item.isSelected
                              ? Icons.check_circle_rounded
                              : Icons.check_circle_outline_rounded,
                          color: item.isSelected ? mainNavy : mainGray,
                          size: 30.0),
                      onPressed: () =>
                          Provider.of<CartProvider>(context, listen: false)
                              .toggleItemSelection(index),
                    ),
                    Text(item.title,
                        style: TextStyle(
                            fontSize: 14, fontWeight: FontWeight.w600)),
                  ],
                ),
              ),
              IconButton(
                icon: Icon(Icons.close_rounded, color: midGray),
                onPressed: () =>
                    Provider.of<CartProvider>(context, listen: false)
                        .removeFromCart(index),
              ),
            ],
          ),
          Padding(
            padding: const EdgeInsets.symmetric(vertical: 4.0),
            child: Row(
              children: [
                SizedBox(width: 48.0),
                Image.network(item.thumbnail, width: 70, height: 70),
                SizedBox(width: 16.0),
                Expanded(
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Text('${item.quantity}개',
                          style: TextStyle(color: midGray)),
                      Text('${item.price}원', style: TextStyle(color: midGray)),
                      QuantityChanger(
                        initialQuantity: item.quantity,
                        maxQuantity: 50, // 재고에 따라 조절
                        onQuantityChanged: (newQuantity) {
                          Provider.of<CartProvider>(context, listen: false)
                              .updateQuantity(index, newQuantity);
                        },
                      ),
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

  Widget _buildTotalPriceButton(int totalPrice) {
    return totalPrice > 0
        ? CustomElevatedButton(
            onPressed: () {
              // TODO: Implement order logic
            },
            text: '${totalPrice}원 주문하기',
            textColor: mainWhite,
            backgroundColor: mainNavy,
          )
        : SizedBox.shrink();
  }

  Widget _buildEmptyCart() {
    return Scaffold(
      backgroundColor: mainWhite,
      body: Center(
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
      ),
    );
  }
}
