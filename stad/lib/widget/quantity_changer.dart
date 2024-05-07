import 'package:flutter/material.dart';
import 'package:stad/constant/colors.dart';

class QuantityChanger extends StatefulWidget {
  final int initialQuantity;
  final int maxQuantity;
  final Function(int) onQuantityChanged;

  const QuantityChanger({
    super.key,
    required this.initialQuantity,
    required this.onQuantityChanged,
    required this.maxQuantity,
  });

  @override
  _QuantityChangerState createState() => _QuantityChangerState();
}

class _QuantityChangerState extends State<QuantityChanger> {
  late int quantity;
  late TextEditingController controller;

  @override
  void initState() {
    super.initState();
    quantity = widget.initialQuantity;
    controller = TextEditingController(text: quantity.toString());
  }

  @override
  void dispose() {
    controller.dispose();
    super.dispose();
  }

  void updateQuantity(String newQuantityStr) {
    int newQuantity = int.tryParse(newQuantityStr) ?? quantity; // 기본값은 이전 수량
    setQuantity(newQuantity);
  }

  void setQuantity(int newQuantity) {
    int adjustedQuantity = newQuantity.clamp(1, widget.maxQuantity);
    if (quantity != adjustedQuantity) {
      setState(() {
        quantity = adjustedQuantity;
        controller.text = quantity.toString(); // 입력 필드 갱신
      });
      widget.onQuantityChanged(quantity);
    }
  }

  void increment() {
    setQuantity(quantity + 1);
  }

  void decrement() {
    if (quantity > 1) {
      setQuantity(quantity - 1);
    }
  }

  @override
  Widget build(BuildContext context) {
    return Row(
      children: [
        _buildCounterButton(Icons.remove, decrement),
        Container(
          width: 40,
          height: 40,
          alignment: Alignment.center,
          child: TextField(
            textAlign: TextAlign.center,
            textAlignVertical: TextAlignVertical.center,
            style: TextStyle(
              fontSize: 16.0, // 글자 크기를 조절합니다.
              height: 1.0, // 텍스트의 높이를 조정하여 세로 중앙 정렬이 되도록 합니다.
            ),
            controller: controller,
            keyboardType: TextInputType.number,
            onSubmitted: updateQuantity,
            decoration: InputDecoration(
              border: InputBorder.none,
            ),
          ),
        ),
        _buildCounterButton(Icons.add, increment),
      ],
    );
  }

  Widget _buildCounterButton(IconData icon, VoidCallback onPressed) {
    return GestureDetector(
      onTap: onPressed,
      child: Container(
        color: Colors.transparent,
        padding: EdgeInsets.symmetric(horizontal: 8, vertical: 4),
        child: Icon(
          icon,
          color: darkGray,
          size: 18.0,
        ),
      ),
    );
  }
}
