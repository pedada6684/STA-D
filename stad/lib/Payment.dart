import 'package:flutter/material.dart';

/* 아임포트 결제 모듈을 불러옵니다. */
import 'package:iamport_flutter/iamport_payment.dart';
/* 아임포트 결제 데이터 모델을 불러옵니다. */
import 'package:iamport_flutter/model/payment_data.dart';
import 'package:stad/constant/colors.dart';
import 'package:stad/widget/app_bar.dart';

class Payment extends StatefulWidget {
  final String pg;
  final String payMethod;
  final String name;
  final merchantUid;
  final int amount;
  final String buyerName;
  final String buyerTel;
  final String buyerEmail;
  final String buyerAddr;
  final String buyerPostcode;
  final String appScheme;
  final List<int> cardQuota;

  const Payment({
    super.key,
    required this.pg,
    required this.payMethod,
    required this.name,
    required this.merchantUid,
    required this.amount,
    required this.buyerName,
    required this.buyerTel,
    required this.buyerEmail,
    required this.buyerAddr,
    required this.buyerPostcode,
    required this.appScheme,
    this.cardQuota = const [2, 3],
  });

  @override
  State<Payment> createState() => _PaymentState();
}

class _PaymentState extends State<Payment> {
  @override
  Widget build(BuildContext context) {
    return IamportPayment(
      appBar: CustomAppBar(
        title: '결제하기',
        showBackButton: true,
      ),
      /* 웹뷰 로딩 컴포넌트 */
      initialChild: Container(
        color: mainWhite,
        child: Center(
          child: Center(
              child: Text('잠시만 기다려주세요...', style: TextStyle(fontSize: 16))),
        ),
      ),
      /* [필수입력] 가맹점 식별코드 */
      userCode: 'iamport',
      /* [필수입력] 결제 데이터 */
      data: PaymentData(
          pg: 'html5_inicis',
          // PG사
          payMethod: 'card',
          // 결제수단
          name: widget.name,
          // 주문명
          merchantUid: 'mid_${DateTime.now().millisecondsSinceEpoch}',
          // 주문번호
          amount: 100,
          // 결제금액
          buyerName: widget.buyerName,
          // 구매자 이름
          buyerTel: widget.buyerTel,
          // 구매자 연락처
          buyerEmail: widget.buyerEmail,
          // 구매자 이메일
          buyerAddr: widget.buyerAddr,
          // 구매자 주소
          buyerPostcode: widget.buyerPostcode,
          // 구매자 우편번호
          appScheme: 'example',
          // 앱 URL scheme
          cardQuota: [2, 3] //결제창 UI 내 할부개월수 제한
          ),
      /* [필수입력] 콜백 함수 */
      callback: (Map<String, String> result) {
        Navigator.pushReplacementNamed(
          context,
          '/result',
          arguments: result,
        );
      },
    );
  }
}
