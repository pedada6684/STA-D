import 'package:flutter/material.dart';
import 'package:stad/constant/colors.dart';

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
      // isOptionExpanded = false;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      decoration: BoxDecoration(
        color: mainWhite,
        borderRadius: BorderRadius.only(
          topLeft: Radius.circular(15.0),
          topRight: Radius.circular(15.0),
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
                color: mainNavy,
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
            _buildQuantityChanger(),
            _buildTotalPrice(),
            _buildActionButtons(context),
          ],
        ),
      ),
    );
  }
}

class CustomDropdown extends StatelessWidget {
  final String title;
  final List<String> options;
  final String? selectedOption;
  final bool isExpanded;
  final Function() onToggle;
  final Function(String?) onSelect;

  const CustomDropdown({
    Key? key,
    required this.title,
    required this.options,
    this.selectedOption,
    required this.isExpanded,
    required this.onToggle,
    required this.onSelect,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      margin: const EdgeInsets.only(bottom: 8.0), // 간격 추가
      decoration: BoxDecoration(
        border: Border.all(color: mainBlack),
        borderRadius: BorderRadius.circular(10.0),
      ),
      child: Column(
        children: [
          ListTile(
            title: Text(title,
                style: TextStyle(fontSize: 16, fontWeight: FontWeight.bold)),
            // 제목 스타일 조정
            subtitle: Text(selectedOption ?? '선택해주세요',
                style: TextStyle(color: mainNavy)),
            // 서브타이틀 스타일 조정
            trailing: Icon(
              isExpanded ? Icons.keyboard_arrow_up : Icons.keyboard_arrow_down,
              color: darkGray,
            ),
            onTap: onToggle,
          ),
          if (isExpanded) // 옵션 위에 구분선 추가
            ...options.map((option) {
              bool isSelected = selectedOption == option;
              return Column(
                children: [
                  Divider(
                    height: 1,
                  ),
                  ListTile(
                    title: Text(option),
                    leading: isSelected
                        ? Icon(Icons.check_circle, color: mainNavy)
                        : Icon(Icons.radio_button_unchecked, color: mainNavy),
                    // 체크박스 아이콘 조정
                    onTap: () {
                      onSelect(isSelected ? null : option); // 선택된 항목을 다시 탭하면 해제
                    },
                  ),
                  // Divider(height: 1), // 옵션 사이의 구분선 추가
                ],
              );
            }).toList(),
        ],
      ),
    );
  }
}

Widget _buildQuantityChanger() {
  // You can use a stateful widget to manage the state of quantity
  int quantity = 0; // Example static quantity
  return Row(
    mainAxisAlignment: MainAxisAlignment.spaceBetween,
    children: [
      Text(
        '민형이가 좋아하는 딸기',
        style: TextStyle(
          fontSize: 16,
          fontWeight: FontWeight.bold,
        ),
      ),
      Row(
        children: [
          _buildCounterButton(Icons.remove, () {
            // 수량 감소
          }),
          Padding(
            padding: const EdgeInsets.symmetric(horizontal: 8.0),
            child: Text('$quantity'),
          ),
          _buildCounterButton(Icons.add, () {
            // 수량 증가
          }),
        ],
      ),
      Text('53,400원',
          style: TextStyle(fontSize: 16, fontWeight: FontWeight.bold)),
    ],
  );
}

Widget _buildCounterButton(IconData icon, VoidCallback onPressed) {
  return IconButton(
    icon: Icon(icon, color: mainNavy),
    onPressed: onPressed,
  );
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
              side: BorderSide(color: mainNavy, width: 2),
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
              //장바구니 담기
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
