import 'dart:async';

import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import 'package:provider/provider.dart';
import 'package:stad/constant/colors.dart';
import 'package:stad/providers/user_provider.dart';
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

          final userProvider =
              Provider.of<UserProvider>(context, listen: false);

          if (userProvider.isLoggedIn) {
            context.go('/home');
          } else {
            context.go('/onboarding');
          }
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
