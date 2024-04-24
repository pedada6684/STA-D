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
      showSelectedLabels: true,
      showUnselectedLabels: false,
      items: <BottomNavigationBarItem>[
        BottomNavigationBarItem(
            icon: Image.asset(
              'assets/image/home1.png',
              width: 25,
              height: 25,
            ),
            label: 'home'),
        BottomNavigationBarItem(
          icon: Image.asset(
            'assets/image/cart.png',
            width: 25,
            height: 25,
          ),
          label: 'cart',
        ),
        BottomNavigationBarItem(
            icon: Image.asset('assets/image/my.png', width: 25,
              height: 25,),
            label: 'my'),
      ],
      currentIndex: selectedIndex,
      selectedItemColor: mainWhite,
      unselectedItemColor: mainWhite,
      onTap: onItemSelected,
    );
  }
}
