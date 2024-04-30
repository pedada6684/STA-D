import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:stad/constant/colors.dart';
import 'package:stad/models/delivery_address_model.dart';
import 'package:stad/providers/user_provider.dart';
import 'package:stad/services/address_service.dart';
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

  late AddressService addressService;
  bool _isLoading = true;
  List<DeliveryAddress> deliveryAddresses = [];

  @override
  void initState() {
    _controller = AnimationController(
        duration: const Duration(milliseconds: 400), vsync: this);
    addressService = AddressService();
    WidgetsBinding.instance.addPostFrameCallback((_) {
      _fetchAddresses();
    });
  }

  @override
  void didChangeDependencies() {
    super.didChangeDependencies();
    // Provider를 통해 userId를 가져옵니다.
    final userProvider = Provider.of<UserProvider>(context);
    if (userProvider.userId != null && deliveryAddresses.isEmpty) {
      _fetchAddresses();
    }
  }

  void _fetchAddresses() async {
    final userProvider = Provider.of<UserProvider>(context, listen: false);
    if (userProvider.userId == null) {
      print("User ID is null, unable to fetch addresses.");
      setState(() {
        _isLoading = false;
      });
      return;
    }

    setState(() => _isLoading = true);
    try {
      deliveryAddresses = await addressService.fetchAddresses(userProvider.userId!);
      setState(() => _isLoading = false);
    } catch (e) {
      print('Failed to fetch addresses: $e');
      setState(() => _isLoading = false);
    }
  }

  void refreshAddresses() {
    _fetchAddresses();
  }

  void _deleteAddress(int userId, int locationId) async {

    try {
      await addressService.deleteAddress(userId, locationId);
      setState(() {
        deliveryAddresses.removeWhere((address) => address.id == locationId);
      });
      ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text('Address successfully deleted')));
    } catch (e) {
      print('Error deleting address: $e');
      ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text('Failed to delete address')));
    }
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
      body: _isLoading
          ? Center(
              child: CircularProgressIndicator(),
            )
          : deliveryAddresses.isNotEmpty
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
              builder: (_) => AddressScreen(onAddressAdded: refreshAddresses));
        },
      ),
    );
  }

  Widget _buildAddressList() {
    final userProvider = Provider.of<UserProvider>(context, listen: false);
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
                    fontSize: 16.0,
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
                    _deleteAddress(userProvider.userId!, address.id);
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

  //
  // void _deleteAddress(int id) {
  //   showDialog(
  //     context: context,
  //     builder: (BuildContext context) {
  //       return AlertDialog(
  //         title: Text('삭제 확인'),
  //         content: Text('배송지를 삭제하시겠습니까?'),
  //         actions: <Widget>[
  //           FlatButton(
  //             child: Text('취소'),
  //             onPressed: () {
  //               Navigator.of(context).pop();
  //             },
  //           ),
  //           FlatButton(
  //             child: Text('삭제'),
  //             onPressed: () async {
  //               Navigator.of(context).pop();
  //               await addressService.deleteAddress(id);
  //               _fetchAddresses();
  //             },
  //           ),
  //         ],
  //       );
  //     },
  //   );
  // }

  Widget _buildEmptyAddress() {
    return Center(
      child: Text(
        '등록된 배송지가 없습니다.\n배송지를 추가해주세요.',
        textAlign: TextAlign.center,
        style: TextStyle(color: midGray, fontSize: 18.0),
      ),
    );
  }
}
