import 'package:flutter/material.dart';
import 'package:stad/constant/colors.dart';
import 'package:stad/services/cart_service.dart';
import 'package:stad/widget/app_bar.dart';
import 'package:stad/widget/button.dart';

class CartScreen extends StatefulWidget {
  const CartScreen({super.key});

  @override
  State<CartScreen> createState() => _CartScreenState();
}

class _CartScreenState extends State<CartScreen> {
  final CartService _cartService = CartService();
  bool _isLoading = true;
  List<dynamic> _cartItems = [];
  Map<int, bool> _selectedItems = {};

  @override
  void initState() {
    super.initState();
    _loadCartItems();
  }

  void _loadCartItems() async {
    try {
      _cartItems = await _cartService.fetchCartItems('1');
      _selectedItems = Map.fromIterable(_cartItems, key: (item) => _cartItems.indexOf(item), value: (item) => false);
    } catch (e) {
      print('Error fetching cart items: $e');
    } finally {
      if (mounted) {
        setState(() {
          _isLoading = false;
        });
      }
    }
  }

  void _toggleItemSelection(int index) {
    setState(() {
      _selectedItems[index] = !_selectedItems[index]!;
    });
  }


  void _selectAllItems() {
    setState(() {
      var allSelected = !_selectedItems.containsValue(false);
      _selectedItems.updateAll((key, value) => !allSelected);
    });
  }

  void _deleteSelectedItems() {
    setState(() {
      _selectedItems.forEach((key, value) {
        if (value) _cartItems.removeAt(key);
      });
      _selectedItems.clear();
      _loadCartItems();
    });
  }

  Widget _buildCartList() {
    return ListView.builder(
      itemCount: _cartItems.length,
      itemBuilder: (context, index) {
        final item = _cartItems[index]; // Assume 'item' is a Map that contains 'product' key
        bool isSelected = _selectedItems[index] ?? false;

        return CheckboxListTile(
          value: isSelected,
          onChanged: (bool? value) {
            _toggleItemSelection(index);
          },
          secondary: IconButton(
            icon: Icon(Icons.close),
            onPressed: () {
              setState(() {
                _cartItems.removeAt(index);
                _selectedItems.remove(index);
              });
            },
          ),
          title: Text(item['product']['name']), // 상품 이름
          subtitle: Text('${item['product']['price']}원'), // 상품 가격
        );
      },
    );
  }

  Widget _buildSelectAndDeleteSection() {
    return Container(
      padding: EdgeInsets.symmetric(horizontal: 16.0, vertical: 8.0),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: [
          TextButton(
            onPressed: _selectAllItems,
            child: Text('전체 선택'),
          ),
          TextButton(
            onPressed: _deleteSelectedItems,
            child: Text('선택 삭제'),
            style: TextButton.styleFrom(foregroundColor: Colors.red),
          ),
        ],
      ),
    );
  }

  Widget _buildCheckoutButton() {
    // 장바구니 합계 계산
    final total = _cartItems.fold<int>(
      0,
          (sum, item) {
        final price = item['product']['price'] as int? ?? 0;
        return sum + price;
      },
    );
    // 체크아웃 버튼
    return Container(
      padding: const EdgeInsets.all(16.0),
      child: CustomElevatedButton(
        text: '${total}원 주문하기',
        onPressed: () {
          // TODO:주문로직 구현하기
        },
        textColor: mainWhite,
        backgroundColor: mainNavy,
      ),
    );
  }

  Widget _buildEmptyCart() {
    // 장바구니가 비어있을 때 표시되는 UI
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
              onPressed: () {
                // 상품 목록 페이지로 이동하는 로직
              },
              textColor: mainWhite,
              backgroundColor: mainGray),
          SizedBox(height: 10.0),
        ],
      ),
    );
  }




  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: mainWhite,
      appBar: CustomAppBar(title: '장바구니', isLoading: _isLoading, progressValue: 0.7),
      body: _isLoading
          ? Center(child: CircularProgressIndicator())
          : _cartItems.isEmpty
          ? _buildEmptyCart()
          : Column(
        children: [
          _buildSelectAndDeleteSection(), // 전체 선택 및 선택 삭제 버튼 추가
          Expanded(child: _buildCartList()), // 장바구니 아이템 리스트
          _buildCheckoutButton(), // 체크아웃 버튼
        ],
      ),
    );
  }
}

  //
  // Widget _buildCartList() {
  //   // 서버에서 받아온 데이터로 장바구니 목록을 구성
  //   return ListView.builder(
  //     itemCount: _cartItems.length,
  //     itemBuilder: (context, index) {
  //       final item = _cartItems[index];
  //       return ListTile(
  //         leading: Image.network(item['thumbnail']), // 상품 썸네일
  //         title: Text(item['name']), // 상품 이름
  //         subtitle: Text('${item['price']}원'), // 상품 가격
  //         trailing: IconButton(
  //           icon: Icon(Icons.remove_shopping_cart),
  //           onPressed: () {
  //             // 장바구니 아이템 삭제 로직
  //           },
  //         ),
  //       );
  //     },
  //   );
  // }
  //
  // Widget _buildCartList() {
  //   // 서버에서 받아온 데이터로 장바구니 목록을 구성
  //   return ListView.builder(
  //     itemCount: _cartItems.length,
  //     itemBuilder: (context, index) {
  //       final item = _cartItems[index]['product']; // 'product' 키로 중첩된 데이터 접근
  //       return ListTile(
  //         leading: Image.asset(item['thumbnail']), // 상품 썸네일
  //         title: Text(item['name']), // 상품 이름
  //         subtitle: Text('${item['price']}원'), // 상품 가격
  //         trailing: IconButton(
  //           icon: Icon(Icons.remove_shopping_cart),
  //           onPressed: () {
  //             // 장바구니 아이템 삭제 로직
  //           },
  //         ),
  //       );
  //     },
  //   );
  // }

  // Widget _buildSelectAndDelete() {
  //   return Container(
  //     padding: EdgeInsets.symmetric(horizontal: 16.0, vertical: 8.0),
  //     child: Row(
  //       mainAxisAlignment: MainAxisAlignment.spaceBetween,
  //       children: [
  //         TextButton.icon(
  //           icon: Icon(Icons.select_all),
  //           label: Text('전체 선택'),
  //           onPressed: _toggleSelectAll,
  //         ),
  //         TextButton(
  //           onPressed: _deleteSelectedItems,
  //           child: Text('선택 삭제'),
  //           style: TextButton.styleFrom(
  //             primary: Colors.red,
  //           ),
  //         ),
  //       ],
  //     ),
  //   );
  // }


