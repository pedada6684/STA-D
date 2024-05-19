import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:stad/constant/colors.dart';
import 'package:stad/models/cart_model.dart';
import 'package:stad/models/product_model.dart';
import 'package:stad/providers/cart_provider.dart';
import 'package:stad/providers/user_provider.dart';
import 'package:stad/screen/order/order_screen.dart';
import 'package:stad/services/cart_service.dart';
import 'package:stad/services/product_service.dart';
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
  late ProductService productService;
  bool isSelectAll = false;

  @override
  void initState() {
    super.initState();
    cartService = CartService();
    productService = ProductService();
    WidgetsBinding.instance.addPostFrameCallback((_) => _loadCartItems());
  }

  void _loadCartItems() async {
    final userId = Provider.of<UserProvider>(context, listen: false).userId;
    List<CartItem> items = await cartService.fetchCartProducts(userId!);
    Provider.of<CartProvider>(context, listen: false).setCartItems(items);
    setState(() {});
  }

  void navigateToOrderScreen() async {
    // 선택된 항목들만 필터링
    List<CartItem> selectedItems =
    Provider.of<CartProvider>(context, listen: false)
        .cartItems
        .where((item) => item.isSelected)
        .toList();

    if (selectedItems.isNotEmpty) {
      List<ProductType> productTypes = [];
      Map<int, ProductInfo> productInfos = {};

      // 각 선택된 아이템의 productId로 ProductInfo를 가져옵니다.
      for (var item in selectedItems) {
        int productId = int.parse(item.id);
        if (!productInfos.containsKey(productId)) {
          productInfos[productId] = await productService.getProductInfo(productId);
        }

        productTypes.add(ProductType(
          id: productId,
          name: item.title,
          price: item.price,
          quantity: item.quantity,
        ));
      }

      Navigator.push(
        context,
        MaterialPageRoute(
          builder: (context) => OrderScreen(
            productInfo: productInfos.isNotEmpty ? productInfos.values.first : null,
            productTypes: productTypes,
            title: productInfos.isNotEmpty ? productInfos.values.first.name : "Order Details",
            quantities: {for (var item in productTypes) item.id: item.quantity},
            advertId: 1, // 예시 ID, 적절한 값으로 변경 필요
            contentId: 1, // 예시 ID, 적절한 값으로 변경 필요
            optionIds: selectedItems.map((item) => item.option?.id ?? -1).toList(),
            deliveryFee: 2500, // 배송료 처리
          ),
        ),
      );
    } else {
      ScaffoldMessenger.of(context)
          .showSnackBar(SnackBar(content: Text("선택된 상품이 없습니다.")));
    }
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
            child:
            Consumer<CartProvider>(builder: (context, cartProvider, child) {
              return StreamBuilder<List<CartItem>>(
                stream: cartProvider.cartItemsStream,
                builder: (context, snapshot) {
                  if (snapshot.connectionState == ConnectionState.waiting) {
                    return Center(
                        child: CircularProgressIndicator(
                          color: mainNavy,
                        ));
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
      bottomNavigationBar: Consumer<CartProvider>(
        builder: (context, cartProvider, child) {
          return _buildTotalPriceButton();
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
              crossAxisAlignment: CrossAxisAlignment.center,
              children: [
                SizedBox(width: 48.0),
                Image.network(item.thumbnail, width: 80, height: 80),
                SizedBox(width: 16.0),
                Expanded(
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Text('${item.title}', style: TextStyle(color: midGray)),
                      Text('${item.quantity}개',
                          style: TextStyle(color: midGray)),
                      Text('${item.price}원', style: TextStyle(color: midGray)),
                      if (item.option != null)
                        Text('옵션: ${item.option!.name} (${item.option!.value}원)', style: TextStyle(color: midGray)),
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

  Widget _buildTotalPriceButton() {
    return Consumer<CartProvider>(
      builder: (context, cartProvider, child) {
        int totalPrice =
        cartProvider.getTotalSelectedPrice(); // 선택된 상품들의 총 금액을 계산
        return totalPrice > 0
            ? CustomElevatedButton(
          onPressed: navigateToOrderScreen,
          text: '${totalPrice}원 주문하기',
          textColor: mainWhite,
          backgroundColor: mainNavy,
        )
            : SizedBox.shrink();
      },
    );
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
