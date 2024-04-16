import 'package:flutter/material.dart';
import 'package:marquee/marquee.dart';
import 'package:stad/constant/colors.dart';

class HomeScreen extends StatefulWidget {
  const HomeScreen({super.key});

  @override
  State<HomeScreen> createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> with WidgetsBindingObserver {
  bool _isActive = true;

  @override
  void initState() {
    super.initState();
    WidgetsBinding.instance.addObserver(this);
  }

  @override
  void dispose() {
    WidgetsBinding.instance.removeObserver(this);
    super.dispose();
  }

  @override
  void didChangeAppLifecycleState(AppLifecycleState state) {
    if (state == AppLifecycleState.resumed) {
      // 앱이 다시 활성화되었을 때
      setState(() {
        _isActive = true;
      });
    } else if (state == AppLifecycleState.paused) {
      // 앱이 비활성화되었을 때
      setState(() {
        _isActive = false;
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: mainWhite,
        centerTitle: true,
        title: Text(
          'STA:D',
          style: TextStyle(
              color: mainNavy, fontFamily: 'LogoFont', fontSize: 40.0),
        ),
      ),
      body: Column(
        children: [
          Container(
            height: 48.0,
            width: MediaQuery.of(context).size.width,
            color: mainNavy,
            child: _isActive
                ? Marquee(
                    text: 'WELCOME TO STA:D!                  ',
                    style: TextStyle(
                        color: mainWhite,
                        fontSize: 18.0,
                        fontWeight: FontWeight.bold),
                    scrollAxis: Axis.horizontal,
                    crossAxisAlignment: CrossAxisAlignment.center,
                    blankSpace: 20.0,
                    velocity: 50.0,
                    startPadding: 10.0,
                    accelerationDuration: Duration.zero,
                    accelerationCurve: Curves.linear,
                    decelerationDuration: Duration.zero,
                    decelerationCurve: Curves.easeOut,
                  )
                : Container(),
          ),
        ],
      ),
    );
  }
}
