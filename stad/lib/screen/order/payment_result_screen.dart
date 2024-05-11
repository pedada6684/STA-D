import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import 'package:provider/provider.dart';
import 'package:stad/constant/colors.dart';
import 'package:stad/providers/user_provider.dart';
import 'package:stad/services/order_service.dart';
import 'package:stad/widget/app_bar.dart';
import 'package:confetti/confetti.dart';
import 'package:stad/widget/button.dart';

class PaymentResultScreen extends StatefulWidget {
  final int userId;
  final Map<String, String> result;
  final List<Map<String, dynamic>> products;

  const PaymentResultScreen(
      {super.key,
      required this.userId,
      required this.products,
      required this.result});

  @override
  State<PaymentResultScreen> createState() => _PaymentResultScreenState();
}

class _PaymentResultScreenState extends State<PaymentResultScreen> {
  late ConfettiController _confettiController; // 'late' 키워드 사용

  @override
  void initState() {
    super.initState();
    // ConfettiController 초기화
    _confettiController = ConfettiController(duration: Duration(seconds: 10));
    _confettiController.play();

    if (widget.result['success'] == 'true') {
      createOrder();
    }
  }

  Future<void> createOrder() async {
    final OrderService orderService = OrderService();
    try {
      await orderService.createOrder(
          userId: widget.userId, products: widget.products);
      // 주문 생성 후 추가적인 액션 (예: 성공 알림)을 취할 수 있습니다.
      print('Order successfully created.');
    } catch (e) {
      print('Failed to create order: $e');
    }
  }

  @override
  void dispose() {
    // ConfettiController 해제
    _confettiController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    final userProvider = Provider.of<UserProvider>(context, listen: false);

    return Scaffold(
      backgroundColor: mainWhite,
      appBar: CustomAppBar(
        title: '주문 완료',
        showBackButton: false,
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            ConfettiWidget(
              confettiController: _confettiController,
              blastDirectionality: BlastDirectionality.explosive,
              shouldLoop: false,
              colors: const [
                Colors.green,
                Colors.blue,
                Colors.amber,
                Colors.pink,
                Colors.purple,
                Colors.orange
              ],
            ),
            Text('🙌', style: TextStyle(fontSize: 48)),
            Text(
              '${userProvider.user!.nickname} 님\n주문이 완료되었습니다.',
              style: TextStyle(
                fontSize: 18,
                fontWeight: FontWeight.bold,
              ),
              textAlign: TextAlign.center,
            ),
            SizedBox(height: 20),
          ],
        ),
      ),
      bottomNavigationBar: CustomElevatedButton(
        text: '메인페이지로 이동하기',
        textColor: mainWhite,
        backgroundColor: mainNavy,
        onPressed: () {
          context.go('/home');
        },
      ),
    );
  }
}
