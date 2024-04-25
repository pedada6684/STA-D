import 'package:flutter/material.dart';
import 'package:stad/constant/colors.dart';
import 'package:stad/widget/address_screen.dart';
import 'package:stad/widget/app_bar.dart';
import 'package:stad/widget/button.dart';

class MyAddressScreen extends StatefulWidget {
  const MyAddressScreen({super.key});

  @override
  State<MyAddressScreen> createState() => _MyAddressScreenState();
}

class _MyAddressScreenState extends State<MyAddressScreen>
    with TickerProviderStateMixin {
  late AnimationController _controller;

  @override
  void initState() {
    _controller =
        AnimationController(duration: Duration(milliseconds: 400), vsync: this);
  }

  @override
  void dispose() {
    _controller.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: mainWhite,
      appBar: CustomAppBar(
        title: '배송지 관리',
        showBackButton: true,
      ),
      body: Center(
          child: Text(
        '등록된 배송지가 없습니다. \n 배송지를 추가해주세요.',
        style: TextStyle(color: midGray),
      )),
      bottomNavigationBar: SafeArea(
        child: Padding(
          padding: const EdgeInsets.all(16.0),
          child: CustomElevatedButton(
            text: '배송지 추가하기',
            textColor: mainWhite,
            backgroundColor: mainNavy,
            onPressed: () {
              showModalBottomSheet(
                  context: context,
                  isScrollControlled: true,
                  useSafeArea: true,
                  transitionAnimationController: _controller,
                  builder: (_) => const AddressScreen());
            },
          ),
        ),
      ),
    );
  }
}
