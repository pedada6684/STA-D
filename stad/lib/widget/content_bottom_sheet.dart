import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:stad/constant/colors.dart';
import 'package:stad/providers/user_provider.dart';
import 'package:stad/services/alert_service.dart';

class ContentDetailBottomSheet extends StatelessWidget {
  final String title;
  final String imagePath;
  final String? additionalText;
  final String? seasonInfo;
  final String? synopsis;
  final int detailId;

  const ContentDetailBottomSheet({
    Key? key,
    required this.title,
    required this.imagePath,
    this.additionalText,
    this.seasonInfo,
    this.synopsis,
    required this.detailId,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final userId = Provider.of<UserProvider>(context, listen: false).userId;

    return DraggableScrollableSheet(
      initialChildSize: 0.5,
      // 처음에 차지할 크기 비율
      maxChildSize: 0.85,
      // 최대 크기 비율
      minChildSize: 0.5,
      // 최소 크기 비율
      expand: false,
      builder: (BuildContext context, ScrollController scrollController) {
        return Container(
          decoration: BoxDecoration(
            color: mainBlack,
            borderRadius: BorderRadius.only(
              topLeft: Radius.circular(20),
              topRight: Radius.circular(20),
            ),
          ),
          child: ListView(
            controller: scrollController,
            children: [
              Stack(
                children: [
                  Image.network(
                    imagePath,
                    fit: BoxFit.cover,
                    height: 280.0,
                  ),
                  Positioned(
                    top: 10,
                    right: 10,
                    child: IconButton(
                      icon: Icon(Icons.close, color: mainWhite),
                      onPressed: () => Navigator.of(context).pop(),
                    ),
                  ),
                  Positioned(
                      bottom: 10,
                      right: 10,
                      child: ElevatedButton(
                          style: ElevatedButton.styleFrom(
                            surfaceTintColor: mainWhite,
                            backgroundColor: mainWhite,
                            foregroundColor: mainNavy,
                            shape: RoundedRectangleBorder(
                                borderRadius: BorderRadius.circular(5.0)),
                          ),
                          onPressed: () {
                            if (userId != null) {
                              AlertService().playContent(userId, detailId);
                            }
                            Navigator.pop(context);
                          },
                          child: Text(
                            '재생하기',
                            style: TextStyle(color: mainNavy, fontSize: 14.0),
                          ))),
                  // Positioned(
                  //   top: 10,
                  //   left: 10,
                  //   child: FloatingActionButton(
                  //     elevation: 0,
                  //     backgroundColor: Colors.transparent,
                  //     onPressed: () {
                  //       if (userId != null) {
                  //         AlertService().playContent(userId, detailId);
                  //       }
                  //       Navigator.pop(context);
                  //     },
                  //     child: Icon(
                  //       Icons.play_circle_outline_rounded,
                  //       color: mainWhite,
                  //       size: 36.0,
                  //     ),
                  //   ),
                  // ),
                ],
              ),
              Padding(
                padding: const EdgeInsets.all(16.0),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text(
                      title,
                      style: TextStyle(
                        color: mainWhite,
                        fontSize: 20,
                        fontWeight: FontWeight.bold,
                      ),
                    ),
                    SizedBox(height: 36.0),
                    if (seasonInfo != null)
                      Text(
                        seasonInfo!,
                        style: TextStyle(color: mainWhite, fontSize: 16),
                      ),
                    SizedBox(
                      height: 24.0,
                    ),
                    if (synopsis != null)
                      Padding(
                        padding: const EdgeInsets.symmetric(vertical: 10.0),
                        child: Text(
                          synopsis!,
                          style: TextStyle(color: mainWhite, fontSize: 14),
                        ),
                      ),
                    SizedBox(
                      height: 24.0,
                    ),
                    Padding(
                      padding: const EdgeInsets.symmetric(vertical: 10.0),
                      child: Text(
                        additionalText ?? '더 많은 정보를 표시할 수 있습니다.',
                        style: TextStyle(color: mainWhite, fontSize: 14),
                      ),
                    ),
                  ],
                ),
              ),
            ],
          ),
        );
      },
    );
  }
}
