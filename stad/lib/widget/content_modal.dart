import 'package:flutter/material.dart';

class ContentDetailDialog extends StatelessWidget {
  final String title;
  final String imagePath;
  final String? additionalText;

  const ContentDetailDialog({
    Key? key,
    required this.title,
    required this.imagePath,
    this.additionalText,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return AlertDialog(
      title: Text(title),
      content: SingleChildScrollView(
        child: Column(
          children: [
            Image.asset(imagePath, fit: BoxFit.cover),
            SizedBox(height: 20),
            Text(additionalText ?? '여기에 추가적인 정보를 표시할 수 있습니다.'),
          ],
        ),
      ),
      actions: <Widget>[
        TextButton(
          child: Text('닫기'),
          onPressed: () => Navigator.of(context).pop(),
        ),
      ],
    );
  }
}
