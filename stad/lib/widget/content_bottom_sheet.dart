import 'package:flutter/material.dart';
import 'package:stad/constant/colors.dart';

class ContentDetailBottomSheet extends StatelessWidget {
  final String title;
  final String imagePath;
  final String? additionalText;
  final String? seasonInfo;
  final String? synopsis;

  const ContentDetailBottomSheet({
    Key? key,
    required this.title,
    required this.imagePath,
    this.additionalText,
    this.seasonInfo,
    this.synopsis,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return DraggableScrollableSheet(
      initialChildSize: 0.6, // 처음에 차지할 크기 비율
      maxChildSize: 0.85, // 최대 크기 비율
      minChildSize: 0.5, // 최소 크기 비율
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
                    height: 250,
                  ),
                  Positioned(
                    top: 10,
                    right: 10,
                    child: IconButton(
                      icon: Icon(Icons.close, color: mainWhite),
                      onPressed: () => Navigator.of(context).pop(),
                    ),
                  ),
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
                        fontSize: 24,
                        fontWeight: FontWeight.bold,
                      ),
                    ),
                    SizedBox(height: 10),
                    if (seasonInfo != null)
                      Text(
                        seasonInfo!,
                        style: TextStyle(color: mainWhite, fontSize: 16),
                      ),
                    if (synopsis != null)
                      Padding(
                        padding: const EdgeInsets.symmetric(vertical: 10.0),
                        child: Text(
                          synopsis!,
                          style: TextStyle(color: mainWhite, fontSize: 14),
                        ),
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
