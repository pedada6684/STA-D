import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:stad/constant/colors.dart';
import 'package:stad/models/order_model.dart';
import 'package:stad/providers/user_provider.dart';
import 'package:stad/services/order_service.dart';
import 'package:stad/widget/app_bar.dart';

class MyOrderScreen extends StatefulWidget {
  const MyOrderScreen({super.key});

  @override
  State<MyOrderScreen> createState() => _MyOrderScreenState();
}

class _MyOrderScreenState extends State<MyOrderScreen> {
  List<OrderDetails>? _orders;
  bool _isLoading = false;

  @override
  void initState() {
    super.initState();
    _fetchOrders();
  }

  Future<void> _fetchOrders() async {
    setState(() => _isLoading = true);
    try {
      final userId = Provider.of<UserProvider>(context, listen: false).userId;
      if (userId != null) {
        final orders = await OrderService().fetchOrders(userId);
        setState(() => _orders = orders);
      }
    } catch (e) {
      print('Failed to fetch orders: $e');
    } finally {
      setState(() => _isLoading = false);
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: backGray,
      appBar: CustomAppBar(title: '주문 내역', showBackButton: true),
      body: _isLoading
          ? Center(child: CircularProgressIndicator())
          : _orders == null || _orders!.isEmpty
              ? Center(
                  child: Text('주문 내역이 없습니다.',
                      style: TextStyle(color: darkGray, fontSize: 18.0)))
              : ListView.builder(
                  itemCount: _orders!.length,
                  itemBuilder: (context, index) {
                    return _buildOrderCard(_orders![index]);
                  },
                ),
    );
  }

  Widget _buildOrderCard(OrderDetails order) {
    return Card(
      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(5)),
      color: mainWhite,
      surfaceTintColor: mainWhite,
      margin: EdgeInsets.all(10),
      elevation: 0,
      child: Column(
        children: [
          ListTile(
            title: Text('${order.orderDate}',
                style: TextStyle(fontWeight: FontWeight.bold)),
          ),
          Divider(
            height: 1,
            color: mainGray,
          ),
          ListView.builder(
            physics: NeverScrollableScrollPhysics(),
            shrinkWrap: true,
            itemCount: order.productDetails.length,
            itemBuilder: (context, index) {
              final product = order.productDetails[index];
              return ListTile(
                // leading: Image.network(product.productImg,
                //     width: 50, height: 50, fit: BoxFit.cover),
                // title: Text(
                //   product.productName,
                //   style: TextStyle(fontWeight: FontWeight.bold),
                // ),
                subtitle: Padding(
                  padding: const EdgeInsets.symmetric(vertical: 10.0),
                  child: Row(
                    children: [
                      Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          Text('결제방법'),
                          _gap(),
                          // Text('수량'),
                          // _gap(),
                          Text('결제금액'),
                          _gap(),
                          Text('주문 상태')
                        ],
                      ),
                      SizedBox(
                        width: 40.0,
                      ),
                      Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          Text('신용카드'),
                          _gap(),
                          // Text('${product.productCnt}'),
                          // _gap(),
                          Text('${product.productPrice} 원'),
                          _gap(),
                          order.orderStatus == 'ORDER'
                              ? Text('주문완료')
                              : Text('취소완료'),
                          _gap(),
                        ],
                      )
                    ],
                  ),
                ),
              );
            },
          ),
        ],
      ),
    );
  }
}

class _gap extends StatelessWidget {
  const _gap();

  @override
  Widget build(BuildContext context) {
    return const SizedBox(
      height: 4.0,
    );
  }
}
