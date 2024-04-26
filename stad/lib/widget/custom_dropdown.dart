import 'package:flutter/material.dart';
import 'package:stad/constant/colors.dart';

class CustomDropdown extends StatelessWidget {
  final String title;
  final List<String> options;
  final String? selectedOption;
  final bool isExpanded;
  final Function() onToggle;
  final Function(String?) onSelect;

  const CustomDropdown({
    super.key,
    required this.title,
    required this.options,
    this.selectedOption,
    required this.isExpanded,
    required this.onToggle,
    required this.onSelect,
  });

  @override
  Widget build(BuildContext context) {
    return Container(
      // margin: const EdgeInsets.only(bottom: 8.0), // 간격 추가
      decoration: BoxDecoration(
        border: Border.all(color: midGray, width: 1),
        borderRadius: BorderRadius.circular(5.0),
      ),
      child: Column(
        children: [
          ListTile(
            title: Text(title,
                style: TextStyle(fontSize: 14, fontWeight: FontWeight.bold)),
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
                    title: Text(option, style: TextStyle(fontSize: 14.0),),
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
