import 'package:flutter/material.dart';
import 'package:stad/widget/app_bar.dart';

class PaymentResultScreen extends StatefulWidget {
  const PaymentResultScreen({super.key});

  @override
  State<PaymentResultScreen> createState() => _PaymentResultScreenState();
}

class _PaymentResultScreenState extends State<PaymentResultScreen> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: CustomAppBar(title: '주문 완료'),
    );
  }
}
