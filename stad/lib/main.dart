// import 'package:firebase_core/firebase_core.dart';
// import 'package:flutter/material.dart';
// import 'package:flutter_dotenv/flutter_dotenv.dart';
// import 'package:provider/provider.dart';
// import 'package:stad/constant/animation/animated_indexed_stack.dart';
// import 'package:stad/firebase_options.dart';
// import 'package:stad/models/cart_model.dart';
// import 'package:stad/providers/user_provider.dart';
// import 'package:stad/screen/cart/cart_screen.dart';
// import 'package:stad/screen/home/home_screen.dart';
// import 'package:stad/screen/home/onboarding_screen.dart';
// import 'package:stad/screen/login/login_screen.dart';
// import 'package:stad/screen/myStad/myStad_screen.dart';
// import 'package:stad/screen/myStad/shop/myaddress_screen.dart';
// import 'package:stad/screen/myStad/shop/myorder_scren.dart';
// import 'package:stad/screen/myStad/shop/myreview_screen.dart';
// import 'package:stad/screen/myStad/stad/mycommercial_screen.dart';
// import 'package:stad/screen/myStad/stad/mycontents_screen.dart';
// import 'package:stad/screen/product/product_screen.dart';
// import 'package:stad/widget/bottom_bar.dart';
// import 'package:go_router/go_router.dart';
//
// import 'providers/cart_provider.dart';
// import 'screen/error/error_screen.dart';
// import 'screen/home/splash_video_screen.dart';
//
// Future<void> main() async {
//   WidgetsFlutterBinding.ensureInitialized(); // Flutter 엔진, 위젯 트리 바인딩
//   await Firebase.initializeApp(
//     options: DefaultFirebaseOptions.currentPlatform,
//   );
//   await dotenv.load(fileName: ".env");
//
//   runApp(const MyApp());
// }
//
// class MyApp extends StatefulWidget {
//   const MyApp({super.key});
//
//   @override
//   State<MyApp> createState() => _MyAppState();
// }
//
// class _MyAppState extends State<MyApp> {
//   int _selectedIndex = 0;
//
//   // late bool _isLoggined;
//   final GlobalKey<NavigatorState> navigatorKey = GlobalKey<NavigatorState>();
//
//   // static const List<Widget> _widgetOptions = <Widget>[
//   //   HomeScreen(),
//   //   CartScreen(),
//   //   MyStadScreen(),
//   // ];
//   //
//   // void _onItemTapped(int index) {
//   //   setState(() {
//   //     _selectedIndex = index;
//   //   });
//   // }
//
//   final GoRouter _router = GoRouter(
//       // errorBuilder: (context, state) => ErrorScreen(error: state.error),
//       debugLogDiagnostics: true,
//       initialLocation: '/splash',
//       routes: <GoRoute>[
//         GoRoute(
//             path: '/splash',
//             builder: (BuildContext context, GoRouterState state) =>
//                 SplashVideoScreen()),
//         GoRoute(
//             path: '/onboarding',
//             builder: (BuildContext context, GoRouterState state) =>
//                 OnboardingScreen()),
//         GoRoute(
//             path: '/login',
//             builder: (BuildContext context, GoRouterState state) =>
//                 LoginScreen()),
//         GoRoute(
//             path: '/',
//             builder: (BuildContext context, GoRouterState state) =>
//                 HomeScreen(),
//             routes: [
//               GoRoute(
//                   path: 'cart',
//                   builder: (BuildContext context, GoRouterState state) =>
//                       CartScreen()),
//               GoRoute(
//                   path: 'mystad',
//                   builder: (BuildContext context, GoRouterState state) =>
//                       MyStadScreen(),
//                   routes: [
//                     GoRoute(
//                         path: 'myaddress',
//                         builder: (BuildContext context, GoRouterState state) =>
//                             MyAddressScreen()),
//                     GoRoute(
//                         path: 'myorder',
//                         builder: (BuildContext context, GoRouterState state) =>
//                             MyOrderScreen()),
//                     GoRoute(
//                         path: 'myreview',
//                         builder: (BuildContext context, GoRouterState state) =>
//                             MyReviewScreen()),
//                     GoRoute(
//                         path: 'mycommercial',
//                         builder: (BuildContext context, GoRouterState state) =>
//                             MyCommercialScreen()),
//                     GoRoute(
//                         path: 'mycontents',
//                         builder: (BuildContext context, GoRouterState state) =>
//                             MyContentsScreen()),
//                   ]),
//               GoRoute(
//                   path: 'product',
//                   builder: (BuildContext context, GoRouterState state) =>
//                       ProductScreen()),
//             ])
//       ]);
//
//   @override
//   Widget build(BuildContext context) {
//     return MultiProvider(
//       providers: [
//         ChangeNotifierProvider(create: (context) => UserProvider()),
//         // Provider(create: (_) => UserService()),
//         ChangeNotifierProvider(
//           create: (context) => CartModel()
//             ..addItem(CartItem(
//               id: '1',
//               title: '더미 상품 1',
//               price: 9900,
//               thumbnail: 'path/to/thumbnail1',
//               quantity: 1,
//               isSelected: true,
//             ))
//             ..addItem(CartItem(
//               id: '2',
//               title: '더미 상품 2',
//               price: 12500,
//               thumbnail: 'path/to/thumbnail2',
//               quantity: 2,
//               isSelected: true,
//             )),
//         ),
//       ],
//       child: MaterialApp.router(
//       routerDelegate: _router.routerDelegate,
//       routeInformationParser: _router.routeInformationParser,
//       routeInformationProvider: _router.routeInformationProvider,
//       title: 'STA:D',
//       theme: ThemeData(
//         visualDensity: VisualDensity.adaptivePlatformDensity,
//         pageTransitionsTheme: PageTransitionsTheme(
//           builders: {
//             TargetPlatform.android: CupertinoPageTransitionsBuilder(),
//           },
//         ),
//         fontFamily: 'MainFont',
//       ),
//       debugShowCheckedModeBanner: false,
//     ),
//
//     );
//   }
// }
import 'package:firebase_core/firebase_core.dart';
import 'package:flutter/material.dart';
import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:go_router/go_router.dart';
import 'package:provider/provider.dart';
import 'package:stad/firebase_options.dart';
import 'package:stad/providers/user_provider.dart';
import 'package:stad/screen/cart/cart_screen.dart';
import 'package:stad/screen/home/home_screen.dart';
import 'package:stad/screen/home/onboarding_screen.dart';
import 'package:stad/screen/login/login_screen.dart';
import 'package:stad/screen/myStad/myStad_screen.dart';
import 'package:stad/widget/bottom_bar.dart';

import 'providers/cart_provider.dart';
import 'screen/home/splash_video_screen.dart';

Future<void> main() async {
  WidgetsFlutterBinding.ensureInitialized();
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
  final GoRouter _router = GoRouter(
    debugLogDiagnostics: true,
    initialLocation: '/splash',
    routes: <GoRoute>[
      GoRoute(
        path: '/splash',
        builder: (BuildContext context, GoRouterState state) => const SplashVideoScreen(),
      ),
      GoRoute(
        path: '/onboarding',
        builder: (BuildContext context, GoRouterState state) => const OnboardingScreen(),
      ),
      GoRoute(
        path: '/login',
        builder: (BuildContext context, GoRouterState state) => const LoginScreen(),
      ),
      GoRoute(
        path: '/',
        builder: (BuildContext context, GoRouterState state) => const MainNavigation(),
      ),
    ],
  );

  @override
  Widget build(BuildContext context) {
    return MultiProvider(
      providers: [
        ChangeNotifierProvider(create: (context) => UserProvider()),
        ChangeNotifierProvider(create: (context) => CartModel()),
      ],
      child: MaterialApp.router(
        routerDelegate: _router.routerDelegate,
        routeInformationParser: _router.routeInformationParser,
        routeInformationProvider: _router.routeInformationProvider,
        title: 'STA:D',
        theme: ThemeData(
          visualDensity: VisualDensity.adaptivePlatformDensity,
          pageTransitionsTheme: PageTransitionsTheme(builders: {
            TargetPlatform.android: CupertinoPageTransitionsBuilder(),
          }),
          fontFamily: 'MainFont',
        ),
        debugShowCheckedModeBanner: false,
      ),
    );
  }
}

class MainNavigation extends StatefulWidget {
  const MainNavigation({super.key});

  @override
  State<MainNavigation> createState() => _MainNavigationState();
}

class _MainNavigationState extends State<MainNavigation> {
  int _selectedIndex = 0;

  final List<Widget> _widgetOptions = [
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
    return Scaffold(
      body: IndexedStack(
        index: _selectedIndex,
        children: _widgetOptions,
      ),
      bottomNavigationBar: CustomBottomNavigationBar(
        selectedIndex: _selectedIndex,
        onItemSelected: _onItemTapped,
      ),
    );
  }
}
