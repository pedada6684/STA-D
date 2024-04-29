import 'package:flutter/material.dart';
import 'package:stad/constant/colors.dart';
import 'package:stad/screen/myStad/stad/content_bottom_sheet.dart';
import 'package:stad/widget/app_bar.dart';

class MyContentsScreen extends StatefulWidget {
  const MyContentsScreen({super.key});

  @override
  State<MyContentsScreen> createState() => _MyContentsScreenState();
}

class _MyContentsScreenState extends State<MyContentsScreen> {
  final Map<String, String> _imagePaths = {
    '존윅: 리로드': 'assets/image/1.png',
    '존윅4': 'assets/image/2.png',
    '존윅3': 'assets/image/3.png',
    '존윅': 'assets/image/4.png',
  };

  @override
  Widget build(BuildContext context) {
    final keys = _imagePaths.keys.toList();
    final values = _imagePaths.values.toList();

    return Scaffold(
      backgroundColor: mainWhite,
      appBar: CustomAppBar(title: '내가 본 콘텐츠'),
      body: GridView.builder(
        padding: const EdgeInsets.all(16),
        gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(
          crossAxisCount: 2,
          crossAxisSpacing: 10,
          mainAxisSpacing: 10,
        ),
        itemCount: _imagePaths.length,
        itemBuilder: (context, index) {
          return InkWell(
            onTap: () => showModalBottomSheet(
              context: context,
              backgroundColor: Colors.transparent,
              builder: (BuildContext context) => ContentDetailBottomSheet(
                title: keys[index],
                imagePath: values[index],
                seasonInfo: 'Season 1, Episode 4',
                synopsis: 'John Wick must navigate...',
              ),
            ),
            child: GridTile(
              child: ClipRRect(
                borderRadius: BorderRadius.all(Radius.circular(5.0)),
                child: Column(
                  children: [
                    Expanded(
                      child: Image.asset(values[index], fit: BoxFit.cover),
                    ),
                    Container(
                      padding: EdgeInsets.all(8),
                      color: Colors.black54,
                      child: Text(
                        keys[index],
                        style: TextStyle(
                          color: Colors.white,
                          fontWeight: FontWeight.bold,
                        ),
                      ),
                    ),
                  ],
                ),
              ),
            ),
          );
        },
      ),
    );
  }
}
