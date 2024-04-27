class CartItem {
  final String id;
  final String title;
  final double price;
  int quantity;
  bool isSelected;

  CartItem({
    required this.id,
    required this.title,
    required this.price,
    this.quantity = 1,
    this.isSelected = false,
  });

  void toggleSelection() {
    isSelected = !isSelected;
  }
}