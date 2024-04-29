import 'package:flutter/material.dart';

class TVSwitchOnRoute extends PageRouteBuilder {
  final Widget page;
  TVSwitchOnRoute({required this.page})
      : super(
    pageBuilder: (
        BuildContext context,
        Animation<double> animation,
        Animation<double> secondaryAnimation,
        ) =>
    page,
    transitionsBuilder: (
        BuildContext context,
        Animation<double> animation,
        Animation<double> secondaryAnimation,
        Widget child,
        ) {
      return ScaleTransition(
        scale: Tween<double>(begin: 0.0, end: 1.0).animate(
            CurvedAnimation(parent: animation, curve: Curves.fastOutSlowIn)
        ),
        child: child,
      );
    },
    transitionDuration: const Duration(milliseconds: 500),
  );
}
