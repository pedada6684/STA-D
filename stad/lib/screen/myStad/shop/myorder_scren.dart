import 'package:flutter/cupertino.dart';
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
  // final List<Map<String, dynamic>> _orders = List.generate(15, (index) {
  //   // Assuming every 3rd item starts a new group with a random date
  //   final isNewDate = index % 3 == 0;
  //   final randomDay = isNewDate ? (1 + index ~/ 3) : 0;
  //   return {
  //     'title': 'Product Title $index',
  //     'description': 'Product Description $index',
  //     'isNewDate': isNewDate,
  //     'date':
  //         isNewDate ? '2024.04.${randomDay.toString().padLeft(2, '1')}' : '',
  //   };
  // });

  List<Order>? _orders;
  bool _isLoading = false;

  @override
  void initState() {
    super.initState();
    Future.microtask(
      () {
        final userProvider = Provider.of<UserProvider>(context, listen: false);
        if (userProvider.userId == null) {
          userProvider.fetchUser().then((_) {
            if (userProvider.userId != null) {
              _fetchOrderData();
            }
          });
        } else {
          _fetchOrderData();
        }
      },
    );
  }

  // Future<void> _initializeUserAndFetchOrders() async {
  //   final userProvider = Provider.of<UserProvider>(context, listen: false);
  //
  //   // fetchUser가 비동기적으로 처리됩니다.
  //   await userProvider.fetchUser();
  //
  //   if (userProvider.userId != null) {
  //     _fetchOrderData();
  //   } else {
  //     setState(() {
  //       _isLoading = false; // 로딩 상태 업데이트
  //       print("User ID is not available. Ensure user is logged in.");
  //     });
  //   }
  // }

  Future<void> _fetchOrderData() async {
    setState(() => _isLoading = true);
    try {
      final userProvider = Provider.of<UserProvider>(context, listen: false);
      final orders = await OrderService().fetchOrders(userProvider.userId!);
      setState(() {
        _orders = orders;
        _isLoading = false;
      });
    } catch (e) {
      print('Error loading orders: $e');
      setState(() {
        _orders = []; // 빈 리스트 할당
        _isLoading = false;
      });
    }
  }

  double _progress = 0.0;

  void simulateLoading() async {
    setState(() {
      _isLoading = true;
      _progress = 0.0;
    });

    int maxSteps = 100; // 예를 들어 100단계의 작업으로 설정
    for (int i = 0; i <= maxSteps; i++) {
      await Future.delayed(Duration(milliseconds: 5), () {
        setState(() {
          _progress = i / maxSteps;
        });
      });
    }

    setState(() {
      _isLoading = false;
      _progress = 0.0;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: mainWhite,
      appBar: CustomAppBar(
        title: '주문 내역',
        showBackButton: true,
        showHomeButton: true,
        isLoading: _isLoading,
        progressValue: _progress,
      ),
      // body: Stack(
      //   children: [
      //     Positioned(
      //       top: 0,
      //       bottom: 0,
      //       left: 72,
      //       child: Container(
      //         width: 1,
      //         color: mainNavy,
      //       ),
      //     ),
      //     CustomScrollView(
      //       slivers: [
      //         SliverToBoxAdapter(
      //             child: SizedBox(
      //           height: 40.0,
      //         )),
      //         SliverList(
      //           delegate: SliverChildBuilderDelegate(
      //             (context, index) {
      //               final order = _orders[index];
      //               return Column(
      //                 crossAxisAlignment: CrossAxisAlignment.start,
      //                 children: [
      //                   if (order['isNewDate'])
      //                     Padding(
      //                       padding: const EdgeInsets.symmetric(
      //                           horizontal: 16.0, vertical: 24.0),
      //                       child: Center(
      //                         child: Text(order['date'],
      //                             style: TextStyle(
      //                                 fontSize: 18,
      //                                 color: mainNavy,
      //                                 fontWeight: FontWeight.w600)),
      //                       ),
      //                     ),
      //                   _buildOrderItem(
      //                     title: order['title'],
      //                     description: order['description'],
      //                   ),
      //                 ],
      //               );
      //             },
      //             childCount: _orders.length,
      //           ),
      //         ),
      //       ],
      //     ),
      //     Positioned(
      //       top: 0,
      //       child: Container(
      //         width: MediaQuery.of(context).size.width,
      //         decoration: BoxDecoration(color: mainWhite),
      //         child: Padding(
      //           padding: const EdgeInsets.all(8.0),
      //           child: Row(
      //             mainAxisAlignment: MainAxisAlignment.end,
      //             children: [
      //               Text(
      //                 '최근 3개월',
      //                 style: TextStyle(
      //                   color: mainNavy,
      //                   fontSize: 16.0,
      //                 ),
      //               ),
      //             ],
      //           ),
      //         ),
      //       ),
      //     ),
      //   ],
      // ),
      body: _isLoading
          ? Center(child: CircularProgressIndicator())
          : _orders == null || _orders!.isEmpty
              ? Center(
                  child: Text(
                  '주문 내역이 없습니다.',
                  style: TextStyle(color: midGray, fontSize: 16.0),
                ))
              : ListView.builder(
                  itemCount: _orders!.length,
                  itemBuilder: (context, index) {
                    final order = _orders![index];
                    return _buildOrderItem(
                      title:
                          order.productTypeName[0], // 예시 코드, 실제 필드명에 맞게 조정 필요
                      description: order.orderDate,
                    );
                  },
                ),
    );
  }

  Widget _buildOrderItem({required String title, required String description}) {
    return Column(
      children: [
        Padding(
          padding: const EdgeInsets.symmetric(horizontal: 32.0, vertical: 10.0),
          child: Row(
            children: [
              Image.asset('assets/image/product.png',
                  width: 80, height: 80, fit: BoxFit.cover),
              // Placeholder image
              SizedBox(width: 20),
              Expanded(
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text(title,
                        style: TextStyle(
                            fontSize: 16, fontWeight: FontWeight.bold)),
                    SizedBox(height: 4),
                    Text(description),
                  ],
                ),
              ),
            ],
          ),
        ),
      ],
    );
  }
}
