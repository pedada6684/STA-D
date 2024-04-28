import 'package:flutter/material.dart';
import 'package:stad/constant/colors.dart';

class QuantityChanger extends StatefulWidget {
  final int initialQuantity;
  final Function(int) onQuantityChanged;

  const QuantityChanger({
    super.key,
    required this.initialQuantity,
    required this.onQuantityChanged,
  });

  @override
  _QuantityChangerState createState() => _QuantityChangerState();
}

class _QuantityChangerState extends State<QuantityChanger> {
  late int quantity;

  @override
  void initState() {
    super.initState();
    quantity = widget.initialQuantity;
  }

  void increment() {
    setState(() {
      quantity++;
      widget.onQuantityChanged(quantity);
    });
  }

  void decrement() {
    if (quantity == 1) return;
    setState(() {
      quantity--;
      widget.onQuantityChanged(quantity);
    });
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
            controller: TextEditingController(text: quantity.toString()),
            keyboardType: TextInputType.number,
            onSubmitted: (newValue) {
              int newQuantity = int.tryParse(newValue) ?? 1;
              setState(() {
                quantity = newQuantity.clamp(1, 99);
              });
            },
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
