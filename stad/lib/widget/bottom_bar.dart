import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:stad/constant/colors.dart';
import 'package:stad/providers/cart_provider.dart';

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
    return Consumer<CartProvider>(
      builder: (context, cartProvider, child) {
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
              label: 'home',
            ),
            BottomNavigationBarItem(
              icon: Stack(
                children: <Widget>[
                  Image.asset(
                    'assets/image/bottombar/cart.png',
                    width: 25,
                    height: 25,
                  ),
                  if (cartProvider.cartItems.length > 0) // Only show badge if there are items
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
                          '${cartProvider.cartItems.length}',
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
              icon: Image.asset(
                'assets/image/bottombar/my.png',
                width: 25,
                height: 25,
              ),
              label: 'my',
            ),
          ],
          currentIndex: selectedIndex,
          selectedItemColor: mainWhite,
          unselectedItemColor: mainWhite,
          onTap: onItemSelected,
        );
      },
    );
  }
}
