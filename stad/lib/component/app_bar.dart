import 'package:flutter/material.dart';
import 'package:stad/constant/colors.dart';

class CustomAppBar extends StatelessWidget implements PreferredSizeWidget {
  final String title;
  final List<Widget>? actions;
  final TextStyle? titleStyle;
  final bool showBackButton;

  const CustomAppBar({
    super.key,
    required this.title,
    this.actions,
    this.titleStyle,
    this.showBackButton = false,
  });

  @override
  Widget build(BuildContext context) {
    return AppBar(
      leading: showBackButton
          ? IconButton(
              icon: Icon(Icons.arrow_back),
              color: mainWhite, // 뒤로가기 버튼의 아이콘 색상을 mainWhite로 지정
              onPressed: () {
                Navigator.pop(context); // 뒤로가기 버튼 클릭 시 이전 화면으로 이동
              },
            )
          : null,
      centerTitle: true,
      title: Text(title, style: titleStyle ?? TextStyle()),
      actions: actions,
      backgroundColor: mainNavy,
    );
  }

  @override
  //사이즈 지정
  Size get preferredSize => const Size.fromHeight(kToolbarHeight);
}
