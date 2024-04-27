import 'package:flutter/material.dart';
import 'package:stad/constant/colors.dart';
import 'package:stad/models/delivery_address_model.dart';
import 'package:stad/widget/address_screen.dart';
import 'package:stad/widget/app_bar.dart';
import 'package:stad/widget/button.dart';
import 'package:stad/widget/product_card.dart';

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
      appBar: const CustomAppBar(
        title: '주문서 작성',
        showBackButton: true,
      ),
      body: SingleChildScrollView(
        child: Column(
          children: [
            _buildOrderHeadContainer(),
            const divider(),
            _buildSelectAds(context),
            const divider(),
            Container(
              padding:
                  const EdgeInsets.symmetric(vertical: 16.0, horizontal: 20.0),
              color: mainWhite, // 배경색 설정
              child: const Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text(
                    '결제금액',
                    style: TextStyle(
                      fontSize: 16.0,
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                  SizedBox(height: 20.0), // 텍스트 사이의 간격
                  Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      Text(
                        '주문금액',
                        style: TextStyle(fontSize: 16.0),
                      ),
                      Text(
                        '53,400 원', // 예시 금액
                        style: TextStyle(
                            fontSize: 16.0, fontWeight: FontWeight.bold),
                      ),
                    ],
                  ),
                  SizedBox(height: 8.0),
                  Padding(
                    padding: const EdgeInsets.all(8.0),
                    child: Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: [
                        Text(
                          '상품할인금액',
                          style: TextStyle(fontSize: 14.0, color: midGray),
                        ),
                        Text(
                          '19,500 원', // 예시 금액
                          style: TextStyle(fontSize: 14.0, color: midGray),
                        ),
                      ],
                    ),
                  ),
                  SizedBox(height: 8.0),
                  Padding(
                    padding: const EdgeInsets.symmetric(horizontal: 8.0),
                    child: Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: [
                        Text(
                          '배송비',
                          style: TextStyle(fontSize: 14.0, color: midGray),
                        ),
                        Text(
                          '0 원', // 예시 금액
                          style: TextStyle(fontSize: 14.0, color: midGray),
                        ),
                      ],
                    ),
                  ),
                  Divider(
                    height: 40,
                    color: mainBlack,
                  ), // 구분선
                  Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      Text(
                        '총 결제금액',
                        style: TextStyle(
                            fontSize: 16.0, fontWeight: FontWeight.w500),
                      ),
                      Text(
                        '53,400 원', // 예시 금액
                        style: TextStyle(
                            fontSize: 20.0, fontWeight: FontWeight.bold),
                      ),
                    ],
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
      bottomNavigationBar: SafeArea(
        child: Padding(
          padding: const EdgeInsets.all(16.0),
          child: CustomElevatedButton(
            text: '결제하기',
            textColor: mainWhite,
            backgroundColor: mainNavy,
            onPressed: () {
              Navigator.of(context)
                  .push(MaterialPageRoute(builder: (context) => ProductCard()));
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
                const Text(
                  '배송지',
                  style: TextStyle(fontSize: 16.0, fontWeight: FontWeight.bold),
                ),
                TextButton(
                  onPressed: () {
                    showModalBottomSheet(
                        context: context,
                        isScrollControlled: true,
                        useSafeArea: true,
                        builder: (_) => const AddressScreen());
                  },
                  child: const Text(
                    '배송지추가',
                    style: TextStyle(
                        decoration: TextDecoration.underline,
                        color: mainNavy,
                        fontSize: 14.0),
                  ),
                ),
              ],
            ),
            deliveryAddresses.isNotEmpty
                ? _buildDeliveryAddressButtons()
                : const Padding(
                    padding: EdgeInsets.all(16.0),
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
          style: const TextStyle(
              fontWeight: FontWeight.bold, fontSize: 16.0, color: mainBlack),
        ),
        const _gap(),
        Text(
          selectedDeliveryAddress!.phone,
          style: const TextStyle(
            fontSize: 14.0,
            color: darkGray,
          ),
        ),
        const _gap(),
        Text(
          selectedDeliveryAddress!.location,
          style: const TextStyle(
            fontSize: 14.0,
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
        child: Theme(
          data: Theme.of(context).copyWith(dividerColor: Colors.transparent),
          child: ExpansionTile(
            title: Text(
              selectedProduct ?? '주문 상품',
              style: const TextStyle(
                  color: mainBlack,
                  fontWeight: FontWeight.bold,
                  fontSize: 16.0),
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
            children: const <Widget>[
              //백에서 받아오기
              ProductCard(),
            ],
          ),
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
              backgroundColor: mainWhite,
              label: Text(
                address.locationNick,
                style: TextStyle(
                  color: isSelected ? mainWhite : mainNavy, // 조건부 색상 지정
                ),
              ),
              showCheckmark: false,
              side: const BorderSide(color: mainNavy),
              shape: const RoundedRectangleBorder(
                borderRadius: BorderRadius.all(
                  Radius.circular(10.0),
                ),
              ),
              selected: isSelected,
              selectedColor: mainNavy,
              onSelected: (bool selected) {
                setState(
                  () {
                    if (selected) {
                      selectedDeliveryAddress = address;
                    }
                  },
                );
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
    return const Divider(
      height: 10,
      thickness: 5,
      color: mainGray,
    );
  }
}

class _gap extends StatelessWidget {
  const _gap();

  @override
  Widget build(BuildContext context) {
    return const SizedBox(
      height: 8.0,
    );
  }
}
