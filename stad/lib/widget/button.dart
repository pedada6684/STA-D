import 'package:flutter/material.dart';
import 'package:stad/constant/colors.dart';

class CustomElevatedButton extends StatelessWidget {
  final String text;
  final VoidCallback? onPressed;
  final Color textColor;
  final Color backgroundColor;

  const CustomElevatedButton({
    super.key,
    required this.text,
    this.onPressed,
    required this.textColor,
    required this.backgroundColor,
  });

  @override
  Widget build(BuildContext context) {
    return SafeArea(
      child: Container(
        child: Padding(
          padding: const EdgeInsets.all(16.0),
          child: ElevatedButton(
            style: ElevatedButton.styleFrom(
              minimumSize: Size(340, 50),
              surfaceTintColor: mainWhite,
              foregroundColor: textColor,
              backgroundColor: onPressed != null ? backgroundColor : mainGray,
              disabledForegroundColor: mainWhite,
              shape: RoundedRectangleBorder(
                borderRadius: BorderRadius.circular(10.0),
              ),
            ),
            onPressed: onPressed,
            child: Padding(
              padding: const EdgeInsets.symmetric(horizontal: 16.0, vertical: 8.0),
              child: Text(
                text,
                style: TextStyle(fontSize: 16.0),
              ),
            ),
          ),
        ),
      ),
    );
  }
}
