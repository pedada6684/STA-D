import 'dart:async';

import 'package:flutter/material.dart';
import 'package:stad/models/cart_model.dart';
import 'package:stad/services/cart_service.dart';

class CartProvider extends ChangeNotifier {
  List<CartItem> _cartItems = [];
  bool _isSelectAll = false;
  final StreamController<List<CartItem>> _cartStreamController =
      StreamController.broadcast();

  List<CartItem> get cartItems => _cartItems;

  Stream<List<CartItem>> get cartItemsStream => _cartStreamController.stream;

  bool get isSelectAll => _isSelectAll;

  void setCartItems(List<CartItem> items) {
    _cartItems = items;
    _isSelectAll = _cartItems.every((item) => item.isSelected);
    _cartStreamController.add(_cartItems);
    notifyListeners();
  }

  Future<void> fetchCartItems(int userId) async {
    var items = await CartService().fetchCartProducts(userId);
    setCartItems(items);
    _cartStreamController.add(items); // 스트림 업데이트 강제화
    notifyListeners(); // 상태 업데이트
  }

  void addToCart(CartItem newItem) {
    int index = _cartItems.indexWhere((item) => item.id == newItem.id);
    if (index != -1) {
      _cartItems[index].quantity += newItem.quantity;
    } else {
      _cartItems.add(newItem);
    }
    _cartStreamController.add(_cartItems);
    notifyListeners();
  }

  void removeFromCart(int index) async {
    int cartProductId = int.parse(_cartItems[index].cartProductId);
    await CartService().deleteCartProducts(cartProductId);

    _cartItems.removeAt(index);
    _cartStreamController.add(_cartItems); // 스트림 업데이트
    notifyListeners();
  }

  void updateQuantity(int index, int newQuantity) {
    if (_cartItems[index].quantity != newQuantity) {
      _cartItems[index].quantity = newQuantity;
      _cartStreamController.add(_cartItems);
      notifyListeners();
    }
  }

  void toggleItemSelection(int index) {
    _cartItems[index].isSelected = !_cartItems[index].isSelected;
    _isSelectAll = _cartItems.every((item) => item.isSelected);
    _cartStreamController.add(_cartItems);
    notifyListeners();
  }

  void toggleSelectAll() {
    _isSelectAll = !_isSelectAll;
    for (var item in _cartItems) {
      item.isSelected = _isSelectAll;
    }
    _cartStreamController.add(_cartItems);
    notifyListeners();
  }

  int getTotalPrice() {
    return _cartItems.fold(
        0,
        (previousValue, element) =>
            previousValue + (element.price * element.quantity));
  }

  @override
  void dispose() {
    _cartStreamController.close();
    super.dispose();
  }
}
