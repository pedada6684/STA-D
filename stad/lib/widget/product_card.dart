import 'package:flutter/material.dart';
import 'package:stad/constant/colors.dart';
import 'package:stad/models/product_model.dart';

class ProductCard extends StatelessWidget {
  final ProductInfo? productInfo;
  final List<ProductType>? productTypes;
  final String? title;
  final int? quantities;
  final int? totalPrice;

  const ProductCard({
    super.key,
    this.productInfo,
    this.productTypes,
    this.title,
    this.quantities,
    this.totalPrice,
  });

  @override
  Widget build(BuildContext context) {
    return Container(
      color: mainWhite,
      child: Padding(
        padding: const EdgeInsets.symmetric(horizontal: 24.0, vertical: 16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            if (productInfo != null)
              Text(
                productInfo!.name,
                style: TextStyle(
                    fontSize: 14.0,
                    color: mainBlack,
                    fontWeight: FontWeight.w600),
              ),
            SizedBox(
              height: 10.0,
            ),
            if (productInfo != null && productTypes != null) ...[
              Row(
                children: [
                  Image.network(
                    productInfo!.thumbnail,
                    width: 120.0,
                  ),
                  SizedBox(
                    width: 16.0,
                  ),
                  Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Text(title!,
                          style: TextStyle(fontSize: 14.0, color: mainBlack)),
                      Text(productTypes!.first.name,
                          style: TextStyle(fontSize: 12.0, color: midGray)),
                      if (productTypes != null &&
                          productTypes!.first.productOptions != [])
                        Text('옵션선택',
                            style: TextStyle(fontSize: 12.0, color: midGray)),
                      Text(quantities.toString(),
                          style: TextStyle(fontSize: 12.0, color: midGray)),
                      Text('${totalPrice.toString()} 원',
                          style: TextStyle(
                              fontSize: 16.0,
                              color: mainBlack,
                              fontWeight: FontWeight.bold)),
                    ],
                  ),
                ],
              )
            ]
          ],
        ),
      ),
    );
  }
}
