import 'package:flutter/material.dart';
import 'package:stad/constant/colors.dart';
import 'package:stad/models/delivery_address_model.dart';
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

  List<DeliveryAddress> deliveryAddresses = [
    DeliveryAddress(
      name: "박지운",
      phone: "010-1000-1000",
      location: "(34153) 대전광역시 유성구 대학로 124",
      locationNick: "집",
    ),
    DeliveryAddress(
      name: "최은희",
      phone: "010-2000-2000",
      location: "(12345) 서울특별시 강남구 테헤란로 123",
      locationNick: "은희집",
    ),
    DeliveryAddress(
      name: "이태경",
      phone: "010-2000-2000",
      location: "(12345) 서울특별시 강남구 테헤란로 123",
      locationNick: "태경집",
    ),
  ];

  @override
  void initState() {
    _controller = AnimationController(
        duration: const Duration(milliseconds: 400), vsync: this);
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
        showHomeButton: true,
      ),
      body: deliveryAddresses.isNotEmpty
          ? _buildAddressList()
          : _buildEmptyAddress(),
      bottomNavigationBar: CustomElevatedButton(
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
    );
  }

  Widget _buildAddressList() {
    return ListView.builder(
      itemCount: deliveryAddresses.length,
      itemBuilder: (context, index) {
        final address = deliveryAddresses[index];
        return Padding(
          padding: const EdgeInsets.symmetric(horizontal: 24.0, vertical: 24.0),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              RichText(
                text: TextSpan(
                  text: '${address.name} ',
                  style: TextStyle(
                    fontSize: 18.0,
                    fontWeight: FontWeight.bold,
                    color: mainBlack, // 이름의 글자 색
                  ),
                  children: <TextSpan>[
                    TextSpan(
                      text: '(${address.locationNick})',
                      style: TextStyle(
                        fontWeight: FontWeight.normal,
                        color: darkGray, // 별명의 글자 색
                      ),
                    ),
                  ],
                ),
              ),
              SizedBox(height: 8.0),
              Text(
                address.phone,
                style: TextStyle(
                  color: darkGray,
                ),
              ),
              SizedBox(height: 8.0),
              Text(
                address.location,
                style: TextStyle(
                  color: darkGray,
                ),
              ),
              SizedBox(height: 16.0),
              Row(
                children: [
                  _buildFlatButton('수정', mainWhite, mainNavy, () {
                    // 수정 버튼 클릭 이벤트
                  }),
                  SizedBox(width: 8.0),
                  _buildFlatButton('삭제', mainNavy, mainWhite, () {
                    // 삭제 버튼 클릭 이벤트
                  }),
                ],
              ),
            ],
          ),
        );
      },
    );
  }

  Widget _buildFlatButton(
      String text, Color color, Color textcolor, VoidCallback onPressed) {
    return SizedBox(
      width: 63,
      height: 35,
      child: ElevatedButton(
        onPressed: onPressed,
        child: Text(text, style: TextStyle(color: textcolor)),
        style: ElevatedButton.styleFrom(
            backgroundColor: color,
            shape: RoundedRectangleBorder(
                borderRadius: BorderRadius.circular(5.0),
                side: BorderSide(color: mainNavy)),
            elevation: 0,
            padding: EdgeInsets.symmetric(horizontal: 16, vertical: 8)),
      ),
    );
  }

  Widget _buildEmptyAddress() {
    return Center(
      child: Text(
        '등록된 배송지가 없습니다.\n배송지를 추가해주세요.',
        textAlign: TextAlign.center,
        style: TextStyle(color: midGray),
      ),
    );
  }
}
