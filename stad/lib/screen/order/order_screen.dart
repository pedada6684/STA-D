import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:stad/Payment.dart';
import 'package:stad/constant/colors.dart';
import 'package:stad/models/delivery_address_model.dart';
import 'package:stad/models/product_model.dart';
import 'package:stad/providers/user_provider.dart';
import 'package:stad/services/address_service.dart';
import 'package:stad/widget/address_screen.dart';
import 'package:stad/widget/app_bar.dart';
import 'package:stad/widget/button.dart';
import 'package:stad/widget/product_card.dart';

class OrderScreen extends StatefulWidget {
  final ProductInfo? productInfo;
  final List<ProductType>? productTypes;
  final String? title;
  final int deliveryFee;
  final Map<int, int> quantities;
  final List<int> optionIds;
  final int advertId;
  final int contentId;

  const OrderScreen({
    super.key,
    this.productInfo,
    this.productTypes,
    this.title,
    this.deliveryFee = 2500,
    required this.quantities,
    required this.advertId,
    required this.contentId,
    required this.optionIds,
  });

  @override
  State<OrderScreen> createState() => _OrderScreenState();
}

class _OrderScreenState extends State<OrderScreen> {
  String? selectedProduct;
  bool isExpanded = false;
  List<DeliveryAddress> deliveryAddresses = [];
  DeliveryAddress? selectedDeliveryAddress;
  int deliveryFee = 0;

  int calculateTotalPrice() {
    int total = 0;
    widget.productTypes?.forEach((product) {
      int quantity = widget.quantities[product.id] ?? 0;
      total += product.price * quantity;
    });
    return total;
  }

  @override
  void initState() {
    super.initState();
    fetchDeliveryAddresses();
  }

  void refreshAddresses() {
    fetchDeliveryAddresses();
  }

  void fetchDeliveryAddresses() async {
    final userProvider = Provider.of<UserProvider>(context, listen: false);
    if (userProvider.userId != null) {
      AddressService addressService = AddressService();
      try {
        deliveryAddresses =
            await addressService.fetchAddresses(userProvider.userId!);
        if (deliveryAddresses.isNotEmpty) {
          selectedDeliveryAddress = deliveryAddresses.first;
        }
      } catch (e) {
        print("Failed to fetch addresses: $e");
      }
    }
  }

  @override
  Widget build(BuildContext context) {
    int totalPrice = calculateTotalPrice();
    int deliveryFee =
        totalPrice >= 30000 ? 0 : widget.productInfo!.cityDeliveryFee;
    String name = widget.productInfo!.name;

    void navigateToPayment(int totalPrice, UserProvider userProvider) {
      if (selectedDeliveryAddress != null) {
        String fullAddress = selectedDeliveryAddress!.location;
        String postcode =
            fullAddress.substring(0, 5); // First 5 characters are the postcode
        String addressDetail =
            fullAddress.substring(6); // Rest is the detailed address

        Navigator.of(context).push(MaterialPageRoute(
          builder: (context) => Payment(
              advertId: widget.advertId,
              contentId: widget.contentId,
              optionIds: widget.optionIds,
              quantities: widget.quantities,
              productTypes: widget.productTypes ?? [],
              // productInfo: widget.productInfo,
              pg: 'html5_inicis',
              payMethod: 'card',
              name: name,
              merchantUid: 'mid_${DateTime.now().millisecondsSinceEpoch}',
              amount: totalPrice,
              buyerName: userProvider.user?.name ?? 'Guest',
              buyerTel: userProvider.user?.phone ?? '010-0000-0000',
              buyerEmail: userProvider.user?.email ?? 'example@example.com',
              buyerAddr: addressDetail,
              buyerPostcode: postcode,
              appScheme: 'example',
              cardQuota: [2, 3]),
        ));
      }
    }

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
            _buildTotalPrice(
              totalPrice: totalPrice,
              deliveryFee: deliveryFee,
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
            onPressed: () => navigateToPayment(
                totalPrice, Provider.of<UserProvider>(context, listen: false)),
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
                        shape: RoundedRectangleBorder(
                            borderRadius: BorderRadius.only(
                                topLeft: Radius.circular(20.0),
                                topRight: Radius.circular(20.0))),
                        useSafeArea: true,
                        builder: (_) =>
                            AddressScreen(onAddressAdded: refreshAddresses));
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
                    child: Text(
                      '배송지를 추가해주세요.',
                      style: TextStyle(color: darkGray),
                    ),
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
            title: Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                Text(
                  selectedProduct ?? '주문 상품',
                  style: const TextStyle(
                      color: mainBlack,
                      fontWeight: FontWeight.bold,
                      fontSize: 16.0),
                ),
                Text(
                  isExpanded ? '' : widget.productInfo?.name ?? '',
                  style: const TextStyle(color: mainBlack, fontSize: 14.0),
                  overflow: (widget.productInfo?.name?.length ?? 0) > 10
                      ? TextOverflow.ellipsis
                      : null, // Apply ellipsis based on text length
                  maxLines: 1,
                ),
              ],
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
            children: widget.productTypes?.map((productType) {
                  int quantity = widget.quantities[productType.id] ?? 0;
                  int totalPrice = quantity * productType.price;
                  return ProductCard(
                    productInfo: widget.productInfo,
                    productTypes: [productType],
                    title: widget.title,
                    quantities: quantity,
                    totalPrice: totalPrice,
                  );
                }).toList() ??
                [],
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
                  color: isSelected ? mainWhite : mainNavy,
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
                      print(address.location);
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

class _buildTotalPrice extends StatelessWidget {
  final int totalPrice;
  final int deliveryFee;

  const _buildTotalPrice({
    super.key,
    required this.totalPrice,
    required this.deliveryFee,
  });

  @override
  Widget build(BuildContext context) {
    return Container(
      padding: const EdgeInsets.symmetric(vertical: 16.0, horizontal: 20.0),
      color: mainWhite,
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Text(
            '결제금액',
            style: TextStyle(
              fontSize: 16.0,
              fontWeight: FontWeight.bold,
            ),
          ),
          SizedBox(height: 20.0),
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              Text(
                '주문금액',
                style: TextStyle(fontSize: 16.0),
              ),
              Text(
                '${totalPrice.toString()} 원',
                style: TextStyle(fontSize: 16.0, fontWeight: FontWeight.bold),
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
                  '0 원',
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
                  '${deliveryFee.toString()} 원',
                  style: TextStyle(fontSize: 14.0, color: midGray),
                ),
              ],
            ),
          ),
          Divider(
            height: 40,
            color: mainBlack,
          ),
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              Text(
                '총 결제금액',
                style: TextStyle(fontSize: 16.0, fontWeight: FontWeight.w500),
              ),
              Text(
                '${deliveryFee + totalPrice} 원',
                style: TextStyle(fontSize: 20.0, fontWeight: FontWeight.bold),
              ),
            ],
          ),
        ],
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
