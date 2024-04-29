import 'package:flutter/material.dart';
import 'package:stad/constant/colors.dart';

class ProductCard extends StatelessWidget {
  const ProductCard({super.key});

  @override
  Widget build(BuildContext context) {
    return Container(
      color: mainWhite,
      child: Padding(
        padding: const EdgeInsets.symmetric(horizontal: 24.0, vertical: 16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              '제조사 명',
              style: TextStyle(
                  fontSize: 14.0,
                  color: mainBlack,
                  fontWeight: FontWeight.w600),
            ),
            SizedBox(
              height: 10.0,
            ),
            Row(
              children: [
                Image.asset(
                  'assets/image/product.png',
                  width: 120.0,
                ),
                SizedBox(
                  width: 16.0,
                ),
                Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text('상품명임',
                        style: TextStyle(fontSize: 14.0, color: mainBlack)),
                    Text('상품선택',
                        style: TextStyle(fontSize: 12.0, color: midGray)),
                    Text('옵션선택',
                        style: TextStyle(fontSize: 12.0, color: midGray)),
                    Text('수량',
                        style: TextStyle(fontSize: 12.0, color: midGray)),
                    Text('얼마얼마 원',
                        style: TextStyle(fontSize: 14.0, color: mainBlack)),
                  ],
                ),
              ],
            )
          ],
        ),
      ),
    );
  }
}
