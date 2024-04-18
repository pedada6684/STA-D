import 'package:flutter/material.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:stad/component/bottom_bar.dart';
import 'package:stad/constant/animated_indexed_stack.dart';
import 'package:stad/firebase_options.dart';
import 'package:stad/screen/cart/cart_screen.dart';
import 'package:stad/screen/home/home_screen.dart';
import 'package:stad/screen/myStad/myStad_screen.dart';

Future<void> main() async {
  WidgetsFlutterBinding.ensureInitialized(); // 필수: Flutter 엔진과 위젯 트리 바인딩
  await Firebase.initializeApp(
    options: DefaultFirebaseOptions.currentPlatform,
  );
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  int _selectedIndex = 0;

  static const List<Widget> _widgetOptions = <Widget>[
    HomeScreen(),
    CartScreen(),
    MyStadScreen(),
  ];

  void _onItemTapped(int index) {
    setState(() {
      _selectedIndex = index;
      // print(_selectedIndex);
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      home: Scaffold(
        body: AnimatedIndexedStack(
          index: _selectedIndex,
          children: _widgetOptions,
        ),
        bottomNavigationBar: CustomBottomNavigationBar(
          selectedIndex: _selectedIndex,
          onItemSelected: _onItemTapped,
        ),
      ),
    );
  }
}
