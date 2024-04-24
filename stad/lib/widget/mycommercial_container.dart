import 'package:flutter/material.dart';

class MyCommercialContainer extends StatefulWidget {
  const MyCommercialContainer({Key? key}) : super(key: key);

  @override
  _MyCommercialContainerState createState() => _MyCommercialContainerState();
}

class _MyCommercialContainerState extends State<MyCommercialContainer>
    with TickerProviderStateMixin {
  AnimationController? _frontController;
  Animation<double>? _frontAnimation;
  bool _isFrontFlipped = false;

  AnimationController? _backController;
  Animation<double>? _backAnimation;
  bool _isBackFlipped = false;

  @override
  void initState() {
    super.initState();

    _frontController = AnimationController(
      vsync: this,
      duration: Duration(milliseconds: 500),
    );
    _frontAnimation = Tween<double>(begin: 0, end: 1).animate(_frontController!)
      ..addListener(() {
        setState(() {});
      });

    _backController = AnimationController(
      vsync: this,
      duration: Duration(milliseconds: 500),
    );
    _backAnimation = Tween<double>(begin: 0, end: 1).animate(_backController!)
      ..addListener(() {
        setState(() {});
      });
  }

  @override
  void dispose() {
    _frontController?.dispose();
    _backController?.dispose();
    super.dispose();
  }

  void _flipFrontCard() {
    if (_isFrontFlipped) {
      _frontController?.reverse();
      _isFrontFlipped = false;
    } else {
      _frontController?.forward();
      _isFrontFlipped = true;
      if (_isBackFlipped) {
        _backController?.reverse();
        _isBackFlipped = false;
      }
    }
  }

  void _flipBackCard() {
    if (_isBackFlipped) {
      _backController?.reverse();
      _isBackFlipped = false;
    } else {
      _backController?.forward();
      _isBackFlipped = true;
      if (_isFrontFlipped) {
        _frontController?.reverse();
        _isFrontFlipped = false;
      }
    }
  }

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        GestureDetector(
          onTap: _flipFrontCard,
          child: AnimatedBuilder(
            animation: _frontAnimation!,
            builder: (context, child) {
              return Transform(
                transform: Matrix4.identity()
                  ..rotateY(_frontAnimation!.value * 3.1415),
                child: _frontAnimation!.value > 0.5
                    ? ColorFiltered(
                        colorFilter: ColorFilter.mode(
                            Colors.black.withOpacity(0.5), BlendMode.darken),
                        child: child,
                      )
                    : child,
                alignment: Alignment.center,
              );
            },
            child: ClipRRect(
              borderRadius: BorderRadius.all(Radius.circular(10.0)),
              child: Image.asset(
                'assets/image/advertising.png',
                height: 130,
              ),
            ),
          ),
        ),
        SizedBox(height: 10),
        // GestureDetector(
        //   onTap: _flipBackCard,
        //   child: AnimatedBuilder(
        //     animation: _backAnimation!,
        //     builder: (context, child) {
        //       return Transform(
        //         transform: Matrix4.identity()
        //           ..rotateY(_backAnimation!.value * 3.1415),
        //         child: _backAnimation!.value > 0.5
        //             ? ColorFiltered(
        //                 colorFilter: ColorFilter.mode(
        //                     Colors.black.withOpacity(0.5), BlendMode.darken),
        //                 child: child,
        //               )
        //             : child,
        //         alignment: Alignment.center,
        //       );
        //     },
        //     child: ClipRRect(
        //       borderRadius: BorderRadius.circular(10),
        //       child: Image.asset(
        //         'assets/image/product.png',
        //         height: 130,
        //         width: MediaQuery.of(context).size.width,
        //         fit: BoxFit.cover,
        //       ),
        //     ),
        //   ),
        // ),
      ],
    );
  }
}
