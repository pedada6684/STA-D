import 'package:flutter/material.dart';
import 'package:stad/constant/colors.dart';

class CustomBottomNavigationBar extends StatelessWidget {
  final int selectedIndex;
  final Function(int) onItemSelected;

  const CustomBottomNavigationBar({
    super.key,
    required this.selectedIndex,
    required this.onItemSelected,
  });

  @override
  Widget build(BuildContext context) {
    return BottomNavigationBar(
      // type: BottomNavigationBarType.fixed,
      backgroundColor: mainNavy,
      items: const <BottomNavigationBarItem>[
        BottomNavigationBarItem(
            icon: Icon(
              Icons.home_outlined,
              color: mainWhite,
              size: 32.0,
            ),
            label: '홈'),
        BottomNavigationBarItem(
          icon: Icon(
            Icons.shopping_cart_outlined,
            color: mainWhite,
            size: 32.0,
          ),
          label: '장바구니',
        ),
        BottomNavigationBarItem(
            icon: Icon(
              Icons.person_outline_rounded,
              color: mainWhite,
              size: 32.0,
            ),
            label: '마이스테디'),
      ],
      currentIndex: selectedIndex,
      selectedItemColor: mainWhite,
      unselectedItemColor: mainWhite,
      onTap: onItemSelected,
    );
  }
}
