import 'package:flutter/material.dart';
import 'package:stad/constant/colors.dart';

class CustomBottomNavigationBar extends StatelessWidget {
  final int selectedIndex;
  final Function(int) onItemSelected;
  final int cartItemCount;

  const CustomBottomNavigationBar({
    super.key,
    required this.selectedIndex,
    required this.onItemSelected,
    this.cartItemCount = 0,
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
              'assets/image/bottombar/home1.png',
              width: 25,
              height: 25,
            ),
            label: 'home'),
        BottomNavigationBarItem(
          icon: Stack(
            children: <Widget>[
              Image.asset(
                'assets/image/bottombar/cart.png',
                width: 25,
                height: 25,
              ),
              if (cartItemCount > 0) // 항목 수가 0보다 크면 뱃지를 표시합니다.
                Positioned(
                  right: 0,
                  child: Container(
                    padding: EdgeInsets.all(1),
                    decoration: BoxDecoration(
                      color: Colors.red,
                      borderRadius: BorderRadius.circular(6),
                    ),
                    constraints: BoxConstraints(
                      minWidth: 12,
                      minHeight: 12,
                    ),
                    child: Text(
                      '$cartItemCount',
                      style: TextStyle(
                        color: Colors.white,
                        fontSize: 8,
                      ),
                      textAlign: TextAlign.center,
                    ),
                  ),
                ),
            ],
          ),
          label: 'cart',
        ),
        BottomNavigationBarItem(
            icon: Image.asset('assets/image/bottombar/my.png', width: 25,
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
