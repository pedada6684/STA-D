import 'package:flutter/material.dart';
import 'package:stad/models/cart_model.dart';

class CartModel with ChangeNotifier {
  final List<CartItem> _items = [];

  List<CartItem> get items => _items.where((item) => item.isSelected).toList();

  int get itemCount => _items.length;

  double get totalPrice =>
      _items.fold(0, (sum, item) => sum + (item.price * item.quantity));

  void addItem(CartItem item) {
    _items.add(item);
    notifyListeners();
  }

  void removeItem(String id) {
    _items.removeWhere((item) => item.id == id);
    notifyListeners();
  }

  void toggleItemSelection(String id) {
    final itemIndex = _items.indexWhere((item) => item.id == id);
    if (itemIndex != -1) {
      _items[itemIndex].toggleSelection();
      notifyListeners();
    }
  }

  void clearCart() {
    _items.clear();
    notifyListeners();
  }
}

class CartProvider extends InheritedWidget {
  final CartModel cart;

  const CartProvider({Key? key, required this.cart, required Widget child})
      : super(key: key, child: child);

  static CartModel of(BuildContext context) {
    final CartProvider? result =
        context.dependOnInheritedWidgetOfExactType<CartProvider>();
    assert(result != null, 'No CartProvider found in context');
    return result!.cart;
  }

  @override
  bool updateShouldNotify(CartProvider old) => cart != old.cart;
}
