import 'dart:async';

import 'package:flutter/material.dart';
import 'package:stad/constant/animation/vertical_split_route.dart';
import 'package:stad/constant/colors.dart';
import 'package:stad/screen/home/onboarding_screen.dart';
import 'package:video_player/video_player.dart';

class SplashVideoScreen extends StatefulWidget {
  final Function onComplete;

  const SplashVideoScreen({super.key, required this.onComplete});

  @override
  State<SplashVideoScreen> createState() => _SplashVideoScreenState();
}

class _SplashVideoScreenState extends State<SplashVideoScreen> {
  late VideoPlayerController _controller;

  @override
  void initState() {
    super.initState();
    _controller = VideoPlayerController.asset('assets/splash/splash_video.mp4')
      ..initialize().then((_) {
        setState(() {});
        _controller.play();
        _controller.setLooping(false);
        _controller.addListener(checkVideoCompletion);
      });
  }

  void checkVideoCompletion() {
    if (!_controller.value.isPlaying &&
        _controller.value.position == _controller.value.duration) {
      _controller.removeListener(checkVideoCompletion);
      widget.onComplete(); // Call the onComplete function passed to the widget
      // 비디오 재생이 완료되면서 3초가 지나는 대신에 바로 OnboardingScreen으로 이동
      Navigator.pushReplacement(context, TVSwitchOnRoute(page: OnboardingScreen()));
    }
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
    _controller.dispose();
    super.dispose();
  }
}
