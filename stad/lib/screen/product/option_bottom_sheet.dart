import 'package:flutter/material.dart';
import 'package:stad/constant/colors.dart';
import 'package:stad/screen/order/order_screen.dart';
import 'package:stad/widget/custom_dropdown.dart';
import 'package:stad/widget/page_animation.dart';
import 'package:stad/widget/quantity_changer.dart';

// 모달 바텀 시트를 띄우는 함수
void showProductOptionBottomSheet(BuildContext context) {
  showModalBottomSheet(
    isScrollControlled: true,
    context: context,
    builder: (BuildContext context) {
      return ProductOptionBottomSheet();
    },
  );
}

// 바텀 시트의 내용을 관리할 StatefulWidget
class ProductOptionBottomSheet extends StatefulWidget {
  const ProductOptionBottomSheet({super.key});

  @override
  _ProductOptionBottomSheetState createState() =>
      _ProductOptionBottomSheetState();
}

class _ProductOptionBottomSheetState extends State<ProductOptionBottomSheet> {
  String? selectedProductOption;
  String? selectedOption;
  bool isProductExpanded = false;
  bool isOptionExpanded = false;

  void toggleProductExpanded() {
    setState(() {
      isProductExpanded = !isProductExpanded;
      if (isOptionExpanded) isOptionExpanded = false;
    });
  }

  void toggleOptionExpanded() {
    setState(() {
      isOptionExpanded = !isOptionExpanded;
      if (isProductExpanded) isProductExpanded = false;
    });
  }

  void selectProductOption(String? option) {
    setState(() {
      selectedProductOption = option;
      isProductExpanded = false;
    });
  }

  void selectOption(String? option) {
    setState(() {
      selectedOption = option;
      isOptionExpanded = false;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      decoration: BoxDecoration(
        color: mainWhite,
        borderRadius: BorderRadius.only(
          topLeft: Radius.circular(25.0),
          topRight: Radius.circular(25.0),
        ),
      ),
      child: Padding(
        padding: const EdgeInsets.symmetric(vertical: 20.0, horizontal: 16.0),
        child: Column(
          mainAxisSize: MainAxisSize.min,
          children: <Widget>[
            Container(
              width: 40,
              height: 4,
              margin: const EdgeInsets.only(bottom: 8.0),
              decoration: BoxDecoration(
                color: darkGray,
                borderRadius: BorderRadius.circular(10),
              ),
            ),
            SizedBox(
              height: 40.0,
            ),
            CustomDropdown(
              title: '상품선택',
              options: ['킹스베리', '설향'],
              //서버에서 받아올 것
              isExpanded: isProductExpanded,
              selectedOption: selectedProductOption,
              onToggle: toggleProductExpanded,
              onSelect: selectProductOption,
            ),
            SizedBox(height: 15),
            CustomDropdown(
              title: '옵션선택',
              options: ['옵션 1', '옵션 2'],
              //서버에서 받아올 것
              isExpanded: isOptionExpanded,
              selectedOption: selectedOption,
              onToggle: toggleOptionExpanded,
              onSelect: selectOption,
            ),
            QuantityChanger(),
            _buildTotalPrice(),
            _buildActionButtons(context),
          ],
        ),
      ),
    );
  }
}

Widget _buildTotalPrice() {
  // Return widget for total price
  return Container(); // Placeholder for total price widget
}

Widget _buildActionButtons(BuildContext context) {
  return Padding(
    padding: const EdgeInsets.only(top: 20.0),
    child: Row(
      children: [
        Expanded(
          child: ElevatedButton(
            style: ElevatedButton.styleFrom(
              foregroundColor: mainNavy,
              textStyle: TextStyle(
                fontSize: 18,
              ),
              side: BorderSide(color: mainNavy, width: 1),
              surfaceTintColor: mainWhite,
              backgroundColor: mainWhite,
              padding: EdgeInsets.symmetric(vertical: 16),
              // Button background color
              shape: RoundedRectangleBorder(
                borderRadius: BorderRadius.all(
                  Radius.circular(10.0),
                ),
              ),
            ),
            onPressed: () {
              //장바구니 담기
            },
            child: Text(
              '장바구니 담기',
            ),
          ),
        ),
        SizedBox(width: 10),
        Expanded(
          child: ElevatedButton(
            style: ElevatedButton.styleFrom(
              foregroundColor: mainWhite,
              textStyle: TextStyle(
                fontSize: 18,
              ),
              surfaceTintColor: mainNavy,
              backgroundColor: mainNavy,
              padding: EdgeInsets.symmetric(vertical: 16),
              // Button background color
              shape: RoundedRectangleBorder(
                borderRadius: BorderRadius.all(
                  Radius.circular(10.0),
                ),
              ),
            ),
            onPressed: () {
              Navigator.push(
                context,
                CustomPageRoute(builder: (context) => OrderScreen()),
              );
            },
            child: Text(
              '구매하기',
            ),
          ),
        ),
      ],
    ),
  );
}
