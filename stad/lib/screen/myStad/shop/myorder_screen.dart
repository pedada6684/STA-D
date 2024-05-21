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
      margin: EdgeInsets.all(10),
      elevation: 0,
      child: ListTile(
        title: Text('${order.orderDate}',
            style: TextStyle(fontWeight: FontWeight.bold)),
        onTap: () {
          showModalBottomSheet(
            context: context,
            isScrollControlled: true,
            builder: (BuildContext context) {
              return OrderReceiptModal(order: order);
            },
          );
        },
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

class OrderReceiptModal extends StatelessWidget {
  final OrderDetails order;

  const OrderReceiptModal({required this.order});

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.all(20.0),
      child: Column(
        mainAxisSize: MainAxisSize.min,
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Center(
            child: Text(
              'e-Receipt / History',
              style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
            ),
          ),
          Center(
            child: Text(
              'STARBUCKS',
              style: TextStyle(fontSize: 24, fontWeight: FontWeight.bold),
            ),
          ),
          Center(
            child: Text(
              '사이렌오더',
              style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
            ),
          ),
          Divider(),
          Text('구역삼사거리점'),
          Text('서울 강남구 논현로 401'),
          Text('대표: 송데이비드호섭'),
          Text('[매장#9447, POS 01]'),
          Divider(),
          Center(
            child: Text(
              '(A-${order.orderId})',
              style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
            ),
          ),
          Divider(),
          ...order.productDetails.map((product) {
            return Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text('T) ${product.productName} ${product.productPrice}원'),
                Text(
                    'L-${product.optionName ?? ''} ${product.optionValue ?? ''}'),
                Divider(),
              ],
            );
          }).toList(),
          Text('결제금액 (부가세포함)'),
          Text(
              '${order.productDetails.fold(0, (sum, item) => sum + item.productPrice)}원'),
          Divider(),
          Text('결제 주문번호'),
          Text('${order.orderId}'),
          Divider(),
          Text('신용카드'),
          Divider(),
          Center(
            child: Text(
              '결제수단 변경은 구입하신 매장에서 가능하며, 반드시 구매 영수증과 원거래 결제수단을 지참하셔야 합니다.',
              style: TextStyle(fontSize: 12),
            ),
          ),
          Center(
            child: Text(
              '(변경 가능 기간: ${order.orderDate})',
              style: TextStyle(fontSize: 12),
            ),
          ),
          SizedBox(height: 20),
        ],
      ),
    );
  }
}
