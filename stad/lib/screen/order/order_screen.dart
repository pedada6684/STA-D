import 'package:flutter/material.dart';
import 'package:stad/constant/colors.dart';
import 'package:stad/models/delivery_address.dart';
import 'package:stad/screen/product/option_bottom_sheet.dart';
import 'package:stad/widget/address_screen.dart';
import 'package:stad/widget/app_bar.dart';
import 'package:stad/widget/button.dart';

class OrderScreen extends StatefulWidget {
  const OrderScreen({super.key});

  @override
  State<OrderScreen> createState() => _OrderScreenState();
}

class _OrderScreenState extends State<OrderScreen> {
  String? selectedProduct;
  bool isExpanded = false;
  String? deliveryAddress; //배송지 주소 저장

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
    super.initState();
    selectedDeliveryAddress = deliveryAddresses.first;
  }

  DeliveryAddress? selectedDeliveryAddress;

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
            _buildOrderHeadContainer(),
            divider(),
            _buildSelectAds(context),
            divider(),
          ],
        ),
      ),
      bottomNavigationBar: SafeArea(
        child: Padding(
          padding: const EdgeInsets.all(16.0),
          child: CustomElevatedButton(
            text: '주문하기',
            textColor: mainWhite,
            backgroundColor: mainNavy,
            onPressed: () {
              showProductOptionBottomSheet(context);
            },
          ),
        ),
      ),
    );
  }

  Container _buildSelectAds(BuildContext context) {
    return Container(
      color: mainWhite,
      child: Padding(
        padding: const EdgeInsets.symmetric(vertical: 16.0, horizontal: 20.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                Text(
                  '배송지',
                  style: TextStyle(fontSize: 20.0, fontWeight: FontWeight.bold),
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
                    '배송지추가',
                    style: TextStyle(
                        decoration: TextDecoration.underline,
                        color: mainNavy,
                        fontSize: 16.0),
                  ),
                ),
              ],
            ),
            deliveryAddresses.isNotEmpty
                ? _buildDeliveryAddressButtons()
                : Padding(
                    padding: const EdgeInsets.all(16.0),
                    child: Text('배송지를 추가해주세요.'),
                  ),
            if (selectedDeliveryAddress != null)
              Padding(
                padding: const EdgeInsets.only(top: 20.0, bottom: 16.0),
                child: _buildDeliveryAds(),
              ),
          ],
        ),
      ),
    );
  }

  Column _buildDeliveryAds() {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Text(
          selectedDeliveryAddress!.name,
          style: TextStyle(
              fontWeight: FontWeight.bold, fontSize: 16.0, color: mainBlack),
        ),
        _gap(),
        Text(
          selectedDeliveryAddress!.phone,
          style: TextStyle(
            fontSize: 16.0,
            color: darkGray,
          ),
        ),
        _gap(),
        Text(
          selectedDeliveryAddress!.location,
          style: TextStyle(
            fontSize: 16.0,
            color: darkGray,
          ),
        ),
      ],
    );
  }

  Container _buildOrderHeadContainer() {
    return Container(
      child: Padding(
        padding: const EdgeInsets.all(8.0),
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
    );
  }

  Widget _buildDeliveryAddressButtons() {
    return SingleChildScrollView(
      scrollDirection: Axis.horizontal,
      child: Row(
        children: deliveryAddresses.map((address) {
          bool isSelected = selectedDeliveryAddress == address;
          return Padding(
            padding: const EdgeInsets.only(right: 16.0),
            child: ChoiceChip(
              surfaceTintColor: mainWhite,
              label: Text(
                address.locationNick,
                style: TextStyle(
                  color: isSelected ? mainWhite : mainNavy, // 조건부 색상 지정
                ),
              ),
              showCheckmark: false,
              side: BorderSide(color: mainNavy),
              shape: RoundedRectangleBorder(
                borderRadius: BorderRadius.all(
                  Radius.circular(10.0),
                ),
              ),
              selected: isSelected,
              selectedColor: mainNavy,
              onSelected: (bool selected) {
                setState(() {
                  if (selected) {
                    selectedDeliveryAddress = address;
                  }
                });
              },
            ),
          );
        }).toList(),
      ),
    );
  }
}

class divider extends StatelessWidget {
  const divider({
    super.key,
  });

  @override
  Widget build(BuildContext context) {
    return Divider(
      height: 10,
      thickness: 5,
      color: mainGray,
    );
  }
}

class _gap extends StatelessWidget {
  const _gap({
    super.key,
  });

  @override
  Widget build(BuildContext context) {
    return SizedBox(
      height: 8.0,
    );
  }
}
