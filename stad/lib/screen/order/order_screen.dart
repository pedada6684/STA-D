import 'package:flutter/material.dart';
import 'package:stad/component/address_screen.dart';
import 'package:stad/component/app_bar.dart';
import 'package:stad/constant/colors.dart';

class OrderScreen extends StatefulWidget {
  const OrderScreen({super.key});

  @override
  State<OrderScreen> createState() => _OrderScreenState();
}

class _OrderScreenState extends State<OrderScreen> {
  String? selectedProduct;
  bool isExpanded = false;
  String? deliveryAddress; //배송지 주소 저장

  final List<String> delieveryAddresses = [];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: mainWhite,
      appBar: CustomAppBar(
        title: '주문서 작성',
        titleStyle: TextStyle(
            color: mainNavy, fontSize: 20, fontWeight: FontWeight.bold),
        showBackButton: true,
      ),
      body: SingleChildScrollView(
        child: Column(
          children: [
            Container(
              margin: EdgeInsets.all(8.0),
              child: ExpansionTile(
                title: Text(
                  selectedProduct ?? '주문 상품',
                  style: TextStyle(color: mainBlack),
                ),
                trailing: Icon(
                  isExpanded
                      ? Icons.keyboard_arrow_up_rounded
                      : Icons.keyboard_arrow_down_rounded,
                  color: mainNavy,
                ),
                onExpansionChanged: (bool expanded) {
                  setState(() {
                    isExpanded = expanded;
                  });
                },
                children: <Widget>[
                  //백에서 받아오기
                  ListTile(title: Text('상품 1')),
                ],
              ),
            ),
            TextButton(
                onPressed: () {
                  showModalBottomSheet(
                      context: context,
                      isScrollControlled: true,
                      useSafeArea: true,
                      builder: (_) => AddressScreen());
                },
                child: Text(
                  '배송지수정',
                  style: TextStyle(
                      decoration: TextDecoration.underline, color: mainNavy),
                ))
          ],
        ),
      ),
    );
  }
}
