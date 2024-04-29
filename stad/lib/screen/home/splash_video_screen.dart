import 'dart:async';

import 'package:flutter/material.dart';
import 'package:stad/constant/animation/vertical_split_route.dart';
import 'package:stad/constant/colors.dart';
import 'package:stad/screen/home/onboarding_screen.dart';
import 'package:video_player/video_player.dart';

class SplashVideoScreen extends StatefulWidget {
  const SplashVideoScreen({super.key});

  @override
  State<SplashVideoScreen> createState() => _SplashVideoScreenState();
}

class _SplashVideoScreenState extends State<SplashVideoScreen> {
  late VideoPlayerController _controller;
  Timer? _timer;

  @override
  void initState() {
    super.initState();
    _controller = VideoPlayerController.asset('assets/splash/splash_video.mp4')
      ..initialize().then((_) {
        setState(() {});
        _controller.play();
        _controller.setLooping(false);
        
        _timer = Timer(Duration(seconds: 3), () {
          _controller.pause(); // 3초 후 비디오를 일시정지
          Navigator.pushReplacement(
              context,
              TVSwitchOnRoute(page: OnboardingScreen()) // 여기에 새 애니메이션 라우트를 적용
          );
        });
      });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: mainBlack,
      body: Center(
        child: _controller.value.isInitialized
            ? Stack(
          children: <Widget>[
            Positioned.fill(
              child: VideoPlayer(_controller),
            ),
          ],
        )
            : Container(),
      ),
    );
  }

  @override
  void dispose() {
    _timer?.cancel();
    _controller.dispose();
    super.dispose();
  }
}