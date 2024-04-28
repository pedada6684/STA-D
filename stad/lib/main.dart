import 'package:firebase_core/firebase_core.dart';
import 'package:flutter/material.dart';
import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:provider/provider.dart';
import 'package:stad/constant/animated_indexed_stack.dart';
import 'package:stad/firebase_options.dart';
import 'package:stad/providers/user_provider.dart';
import 'package:stad/screen/cart/cart_screen.dart';
import 'package:stad/screen/home/home_screen.dart';
import 'package:stad/screen/myStad/myStad_screen.dart';
import 'package:stad/widget/bottom_bar.dart';

import 'providers/cart_provider.dart';

Future<void> main() async {
  WidgetsFlutterBinding.ensureInitialized(); // Flutter 엔진, 위젯 트리 바인딩
  await Firebase.initializeApp(
    options: DefaultFirebaseOptions.currentPlatform,
  );
  await dotenv.load(fileName: ".env");

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
    });
  }

  @override
  Widget build(BuildContext context) {
    return MultiProvider(
      providers: [
        ChangeNotifierProvider(create: (context) => UserProvider()),
        ChangeNotifierProvider(create: (context) => CartModel()),
      ],
      child: MaterialApp(
        title: 'STA:D',
        theme: ThemeData(
          visualDensity: VisualDensity.adaptivePlatformDensity,
          pageTransitionsTheme: PageTransitionsTheme(
            builders: {
              TargetPlatform.android: CupertinoPageTransitionsBuilder(),
            },
          ),
          fontFamily: 'MainFont',
        ),
        debugShowCheckedModeBanner: false,
        home: Scaffold(
          body: AnimatedIndexedStack(
            index: _selectedIndex,
            children: _widgetOptions,
          ),
          bottomNavigationBar: Consumer<CartModel>( // Consumer 위젯 사용
            builder: (context, cart, child) {
              return CustomBottomNavigationBar(
                selectedIndex: _selectedIndex,
                onItemSelected: _onItemTapped,
                cartItemCount: cart.itemCount, // CartModel로부터 장바구니 아이템 수를 가져옴
              );
            },
          ),
        ),
      ),
    );
  }
}
