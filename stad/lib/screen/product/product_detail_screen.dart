import 'package:flutter/material.dart';
import 'package:stad/constant/colors.dart';

class ProductDetailScreen extends StatelessWidget {
  const ProductDetailScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return SingleChildScrollView(
      child: Container(
        color: mainGray,
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: <Widget>[
            Image.asset('assets/image/product.png', fit: BoxFit.cover),
            // const SizedBox(height: 10.0),
            Container(
              color: mainWhite,
              child: const Padding(
                padding: const EdgeInsets.symmetric(
                    vertical: 10.0, horizontal: 20.0),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text(
                      '상품명임',
                      style: TextStyle(
                        fontSize: 18.0,
                        fontWeight: FontWeight.w700,
                      ),
                    ),
                    Text(
                      '당일 산지 직송 킹스베리 베리 딜리셔스~',
                      style: TextStyle(
                        fontSize: 14.0,
                      ),
                    ),
                    SizedBox(
                      height: 32.0,
                    ),
                    Row(
                      children: [
                        Text(
                          '24,300원',
                          style: TextStyle(
                              fontSize: 16.0,
                              fontWeight: FontWeight.w900,
                              color: mainGray,
                              decoration: TextDecoration.lineThrough,
                              decorationColor: mainGray),
                        ),
                        SizedBox(
                          width: 16.0,
                        ),
                        Text(
                          '17,800원',
                          style: TextStyle(
                              fontSize: 18.0,
                              fontWeight: FontWeight.w900,
                              color: mainBlack),
                        ),
                      ],
                    ),
                  ],
                ),
              ),
            ),
            const SizedBox(
              height: 10.0,
            ),
            Container(
              color: mainWhite,
              child: Padding(
                padding: const EdgeInsets.symmetric(
                    horizontal: 20.0, vertical: 16.0),
                child: Column(
                  children: [
                    const Row(
                      children: [
                        Text(
                          '상품정보',
                          style: TextStyle(
                              fontSize: 14.0, fontWeight: FontWeight.bold),
                        ),
                      ],
                    ),
                    const SizedBox(
                      height: 16.0,
                    ),
                    const Row(
                      children: [
                        Text(
                          '제조사',
                          style: TextStyle(fontSize: 14.0),
                        ),
                        SizedBox(
                          width: 15.0,
                        ),
                        Text('민형이네 딸기밭')
                      ],
                    ),
                    const Row(
                      children: [
                        Text(
                          '상품명',
                          style: TextStyle(fontSize: 14.0),
                        ),
                        SizedBox(
                          width: 15.0,
                        ),
                        Text('민형이가 좋아하는 딸기')
                      ],
                    ),
                    const Row(
                      children: [
                        Text(
                          '원산지',
                          style: TextStyle(fontSize: 14.0),
                        ),
                        SizedBox(
                          width: 15.0,
                        ),
                        Text('국산(충청남도 논산시)')
                      ],
                    ),
                    const SizedBox(
                      height: 16.0,
                    ),
                    Image.asset('assets/image/detail1.png'),
                    Image.asset('assets/image/detail2.png'),
                  ],
                ),
              ),
            ),
            const SizedBox(
              height: 10.0,
            ),
            Container(
              color: mainWhite,
              child: Padding(
                padding:
                    const EdgeInsets.symmetric(horizontal: 20, vertical: 16.0),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    const Text(
                      '상품정보 제공 고시',
                      style: TextStyle(
                        fontSize: 14.0,
                        fontWeight: FontWeight.bold,
                      ),
                    ),
                    const Divider(),
                    // SizedBox(height: 20.0), // 섹션 제목과 내용 사이 간격 추가
                    _buildInformationRow('품명 및 모델명', '민형이네 딸기밭'),
                    const SizedBox(height: 10.0), // 각 정보 텍스트 사이 간격 추가
                    _buildInformationRow('제조연월일', '당일 수확 후 배송'),
                    const SizedBox(height: 10.0),
                    _buildInformationRow('원산지', '국산(충청남도 논산시)'),
                    const SizedBox(height: 10.0),
                    _buildInformationRow('생산자', '박지운'),
                    const SizedBox(height: 10.0),
                    _buildInformationRow('상품 구성', '킹스베리 특 1팩(400g, 9~10과)'),
                    const SizedBox(height: 10.0),
                    _buildInformationRow('보관방법', '상품 수령 후 냉장보관 plz'),
                    const SizedBox(height: 10.0),
                    _buildInformationRow('소비자상담 관련 전화번호', '1577-1577'),
                  ],
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildInformationRow(String title, String value) {
    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceBetween,
      children: [
        Text(
          title,
          style: const TextStyle(
            fontSize: 12.0,
          ),
        ),
        Text(
          value,
          style: const TextStyle(
            fontSize: 12.0,
          ),
        ),
      ],
    );
  }
}
