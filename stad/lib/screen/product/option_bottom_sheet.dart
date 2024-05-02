import 'package:flutter/material.dart';
import 'package:stad/constant/colors.dart';
import 'package:stad/models/product_model.dart';
import 'package:stad/screen/order/order_screen.dart';
import 'package:stad/widget/custom_dropdown.dart';
import 'package:stad/widget/page_animation.dart';
import 'package:stad/widget/quantity_changer.dart';

// 모달 바텀 시트를 띄우는 함수
void showProductOptionBottomSheet(BuildContext context,
    ProductInfo? productInfo, List<ProductType> productTypes, String title) {
  showModalBottomSheet(
    isScrollControlled: true,
    context: context,
    builder: (BuildContext context) {
      return ProductOptionBottomSheet(
        productInfo: productInfo,
        productTypes: productTypes,
        title: title,
      );
    },
  );
}

// 바텀 시트의 내용을 관리할 StatefulWidget
class ProductOptionBottomSheet extends StatefulWidget {
  final ProductInfo? productInfo;
  final List<ProductType> productTypes;
  final String title;

  const ProductOptionBottomSheet(
      {super.key,
      this.productInfo,
      required this.productTypes,
      required this.title});

  @override
  _ProductOptionBottomSheetState createState() =>
      _ProductOptionBottomSheetState();
}

class _ProductOptionBottomSheetState extends State<ProductOptionBottomSheet> {
  String? selectedProductOption;
  String? selectedOption;
  bool isProductExpanded = false;
  bool isOptionExpanded = false;
  int? selectedProductIndex;
  int? selectedOptionIndex;

  void selectProductOption(String? value) {
    if (value != null) {
      int index = widget.productTypes.indexWhere((p) => p.name == value);
      setState(() {
        selectedProductIndex = index;
        selectedOptionIndex = null;
        isProductExpanded = false;
        if (widget.productTypes[index].productOptions.isEmpty) {
          isOptionExpanded = false;
        }
      });
    }
  }

  // void toggleProductExpanded() {
  //   setState(() {
  //     isProductExpanded = !isProductExpanded;
  //     if (isOptionExpanded) isOptionExpanded = false;
  //   });
  // }

  void toggleOptionExpanded() {
    setState(() {
      isOptionExpanded = !isOptionExpanded;
      if (isProductExpanded) isProductExpanded = false;
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
    List<String> productOptions =
        widget.productTypes.map((p) => p.name).toList();
    List<String>? currentOptions = selectedProductIndex != null
        ? widget.productTypes[selectedProductIndex!].productOptions
            .map((o) => o.name)
            .toList()
        : null;

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
              options: productOptions,
              //서버에서 받아올 것
              isExpanded: isProductExpanded,
              selectedOption: selectedProductIndex != null
                  ? productOptions[selectedProductIndex!]
                  : null,
              onToggle: () =>
                  setState(() => isProductExpanded = !isProductExpanded),
              // onSelect: (String? value) {
              //   if (value != null) {
              //     int index = productOptions.indexOf(value);
              //     setState(() {
              //       selectedProductIndex = index;
              //       selectedOptionIndex = null; // Reset the selected option
              //     });
              //   }
              // },
              onSelect: selectProductOption,
            ),
            SizedBox(height: 15),
            if (currentOptions != null && currentOptions.isNotEmpty)
              CustomDropdown(
                title: '옵션선택',
                options: currentOptions,
                //서버에서 받아올 것
                isExpanded: isOptionExpanded,
                selectedOption: selectedOptionIndex != null
                    ? currentOptions[selectedOptionIndex!]
                    : null,
                onToggle: toggleOptionExpanded,
                onSelect: (String? value) {
                  setState(() {
                    selectedOptionIndex = currentOptions.indexOf(value!);
                  });
                },
              ),
            if (selectedProductIndex != null) ...[
              ProductDetails(
                productType: widget.productTypes[selectedProductIndex!],
                onCancel: () {
                  setState(() {
                    selectedProductIndex = null;
                    selectedOptionIndex = null;
                  });
                },
              ),
              QuantityChanger(
                initialQuantity: 1,
                onQuantityChanged: (newQuantity) {
                  // Handle quantity change
                },
              ),
            ],
            // Padding(
            //   padding: const EdgeInsets.all(5.0),
            //   child: Row(
            //     mainAxisAlignment: MainAxisAlignment.spaceBetween,
            //     children: [
            //       Text(
            //         '민형이가 좋아하는 딸기',
            //         style: TextStyle(
            //           fontSize: 14,
            //           fontWeight: FontWeight.bold,
            //         ),
            //       ),
            //       IconButton(
            //         iconSize: 20.0,
            //         onPressed: () {},
            //         icon: Icon(
            //           Icons.cancel_rounded,
            //           color: mainGray,
            //         ),
            //       ),
            //     ],
            //   ),
            // ),
            _buildTotalPrice(),
            _buildActionButtons(context),
          ],
        ),
      ),
    );
  }
}

class ProductDetails extends StatelessWidget {
  final ProductType productType;
  final VoidCallback onCancel;

  const ProductDetails(
      {Key? key, required this.productType, required this.onCancel})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceBetween,
      children: [
        Expanded(
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Text(productType.name,
                  style: TextStyle(fontSize: 16, fontWeight: FontWeight.bold)),
              Text("${productType.price}원", style: TextStyle(color: midGray)),
              Text("재고: ${productType.quantity}",
                  style: TextStyle(color: midGray)),
            ],
          ),
        ),
        IconButton(
          iconSize: 20.0,
          onPressed: onCancel,
          icon: Icon(
            Icons.cancel_rounded,
            color: mainGray,
          ),
        ),
      ],
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
                fontSize: 16,
              ),
              side: BorderSide(color: mainNavy, width: 1),
              surfaceTintColor: mainWhite,
              backgroundColor: mainWhite,
              padding: EdgeInsets.symmetric(vertical: 14),
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
                fontSize: 16,
              ),
              surfaceTintColor: mainNavy,
              backgroundColor: mainNavy,
              padding: EdgeInsets.symmetric(vertical: 14),
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
