import 'package:flutter/material.dart';
import 'package:stad/constant/colors.dart';

class QuantityChanger extends StatefulWidget {
  const QuantityChanger({super.key});

  @override
  _QuantityChangerState createState() => _QuantityChangerState();
}

class _QuantityChangerState extends State<QuantityChanger> {
  int quantity = 1;

  void increment() {
    setState(() {
      quantity++;
    });
  }

  void decrement() {
    if (quantity == 1) return;
    setState(() {
      quantity--;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Column(
      mainAxisAlignment: MainAxisAlignment.spaceBetween,
      children: [
        Padding(
          padding: const EdgeInsets.all(5.0),
          child: Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              Text(
                '민형이가 좋아하는 딸기',
                style: TextStyle(
                  fontSize: 14,
                  fontWeight: FontWeight.bold,
                ),
              ),
              IconButton(
                iconSize: 30.0,
                onPressed: () {},
                icon: Icon(
                  Icons.cancel_rounded,
                  color: mainGray,
                ),
              ),
            ],
          ),
        ),
        Padding(
          padding: const EdgeInsets.symmetric(horizontal: 5.0),
          child: Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: [
              Container(
                decoration: BoxDecoration(
                    border: Border.all(color: midGray),
                    shape: BoxShape.rectangle,
                    borderRadius: BorderRadius.circular(5.0)),
                child: Row(
                  crossAxisAlignment: CrossAxisAlignment.center,
                  children: [
                    _buildCounterButton(Icons.remove, decrement),
                    Padding(
                      padding: const EdgeInsets.all(4.0),
                      child: Text(
                        '$quantity',
                        style: TextStyle(fontSize: 14.0),
                      ),
                    ),
                    _buildCounterButton(Icons.add, increment),
                  ],
                ),
              ),
              Text('53,400원',
                  style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold)),
            ],
          ),
        ),
      ],
    );
  }

  Widget _buildCounterButton(IconData icon, VoidCallback onPressed) {
    return GestureDetector(
      onTap: onPressed,
      child: Container(
        color: Colors.transparent, // 버튼 배경 색상을 지정할 수 있습니다.
        padding: EdgeInsets.symmetric(horizontal: 8, vertical: 4),
        child: Icon(
          icon,
          color: darkGray,
        ),
      ),
    );
  }
}
