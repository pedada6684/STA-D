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
      backgroundColor: mainNavy,
      items: const <BottomNavigationBarItem>[
        BottomNavigationBarItem(
            icon: Icon(
              Icons.home_outlined,
              color: mainWhite,
              size: 32.0,
            ),
            label: 'home'),
        BottomNavigationBarItem(
          icon: Icon(
            Icons.shopping_cart_outlined,
            color: mainWhite,
            size: 32.0,
          ),
          label: 'cart',
        ),
        BottomNavigationBarItem(
            icon: Icon(
              Icons.person_outline_rounded,
              color: mainWhite,
              size: 32.0,
            ),
            label: 'my'),
      ],
      currentIndex: selectedIndex,
      selectedItemColor: mainWhite,
      unselectedItemColor: mainWhite,
      onTap: onItemSelected,
    );
  }
}
