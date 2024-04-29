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
    return Container(
      color: mainBlack,
      child: Column(
        mainAxisSize: MainAxisSize.min,
        children: [
          Padding(
            padding: const EdgeInsets.all(16.0),
            child: Row(
              mainAxisAlignment: MainAxisAlignment.end,
              children: [
                IconButton(
                  icon: Icon(Icons.close, color: mainWhite),
                  onPressed: () => Navigator.of(context).pop(),
                )
              ],
            ),
          ),
          Image.asset(imagePath, fit: BoxFit.cover),
          SizedBox(height: 20),
          Padding(
            padding: const EdgeInsets.symmetric(horizontal: 16.0),
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
                  Text(
                    synopsis!,
                    style: TextStyle(color: mainWhite, fontSize: 16),
                  ),
                SizedBox(height: 10),
                Text(
                  additionalText ?? '더 많은 정보를 표시할 수 있습니다.',
                  style: TextStyle(color: mainWhite, fontSize: 14),
                ),
              ],
            ),
          ),
          SizedBox(height: 20),
        ],
      ),
    );
  }
}
