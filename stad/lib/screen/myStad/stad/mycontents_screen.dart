import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:stad/constant/colors.dart';
import 'package:stad/providers/user_provider.dart';
import 'package:stad/widget/content_bottom_sheet.dart';
import 'package:stad/services/contents_service.dart';
import 'package:stad/widget/app_bar.dart';

class MyContentsScreen extends StatefulWidget {
  const MyContentsScreen({super.key});

  @override
  State<MyContentsScreen> createState() => _MyContentsScreenState();
}

class _MyContentsScreenState extends State<MyContentsScreen> {
  List<Map<String, dynamic>> watchedContents = [];
  bool isLoading = true;

  // final Map<String, String> _imagePaths = {
  //   '존윅: 리로드': 'assets/image/1.png',
  //   '존윅4': 'assets/image/2.png',
  //   '존윅3': 'assets/image/3.png',
  //   '존윅': 'assets/image/4.png',
  // };

  @override
  void initState() {
    super.initState();
    fetchWatchedContents();
  }

  void fetchWatchedContents() async {
    int userId = Provider.of<UserProvider>(context, listen: false).userId ?? 0;
    if (userId != 0) {
      try {
        watchedContents = await ContentsService().fetchWatchedcontents(userId);
      } catch (e) {
        print('콘텐츠 불러오다가 에러 , mycontents_screen:$e');
      }
    }
    setState(() => isLoading = false);
  }

  @override
  Widget build(BuildContext context) {
    // final keys = _imagePaths.keys.toList();
    // final values = _imagePaths.values.toList();

    return Scaffold(
      backgroundColor: mainWhite,
      appBar: CustomAppBar(
        title: '내가 본 콘텐츠',
        showBackButton: true,
        showHomeButton: true,
      ),
      body: isLoading
          ? Center(
              child: CircularProgressIndicator(),
            )
          : watchedContents.isEmpty
              ? Center(
                  child: Text(
                    '내가 본 콘텐츠가 없습니다.',
                    style: TextStyle(color: darkGray, fontSize: 18.0),
                  ),
                )
              : GridView.builder(
                  padding: const EdgeInsets.all(16),
                  gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(
                    crossAxisCount: 2,
                    crossAxisSpacing: 10,
                    mainAxisSpacing: 10,
                    // childAspectRatio: 0.9, // 이미지 비율
                  ),
                  itemCount: watchedContents.length,
                  itemBuilder: (context, index) {
                    final content = watchedContents[index];

                    return InkWell(
                      onTap: () => showDialog(
                        context: context,
                        builder: (BuildContext context) =>
                            ContentDetailBottomSheet(
                          title: content['title'],
                          imagePath: content['thumbnailUrl'],
                          additionalText: '서버에서 받아올 정보',
                        ),
                      ),
                      child: GridTile(
                        child: ClipRRect(
                          borderRadius: BorderRadius.all(Radius.circular(5.0)),
                          child: Column(
                            children: [
                              Expanded(
                                child: Image.network(content['thumbnailUrl'],
                                    fit: BoxFit.cover),
                              ),
                              Container(
                                padding: EdgeInsets.all(8),
                                child: Text(
                                  content['title'],
                                  style: TextStyle(
                                    color: mainBlack,
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
//
// import 'package:flutter/material.dart';
// import 'package:stad/constant/colors.dart';
// import 'package:stad/widget/app_bar.dart';
// import 'package:stad/screen/myStad/stad/content_bottom_sheet.dart';  // 경로 확인 필요
//
// class MyContentsScreen extends StatefulWidget {
//   const MyContentsScreen({super.key});
//
//   @override
//   State<MyContentsScreen> createState() => _MyContentsScreenState();
// }
//
// class _MyContentsScreenState extends State<MyContentsScreen> {
//   final Map<String, String> _imagePaths = {
//     '존윅: 리로드': 'assets/image/1.png',
//     '존윅4': 'assets/image/2.png',
//     '존윅3': 'assets/image/3.png',
//     '존윅': 'assets/image/4.png',
//   };
//
//   @override
//   Widget build(BuildContext context) {
//     final keys = _imagePaths.keys.toList();
//     final values = _imagePaths.values.toList();
//
//     return Scaffold(
//       backgroundColor: mainWhite,
//       appBar: CustomAppBar(title: '내가 본 콘텐츠'),
//       body: GridView.builder(
//         padding: const EdgeInsets.all(16),
//         gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(
//           crossAxisCount: 2,
//           crossAxisSpacing: 10,
//           mainAxisSpacing: 10,
//         ),
//         itemCount: _imagePaths.length,
//         itemBuilder: (context, index) {
//           return InkWell(
//             onTap: () => showModalBottomSheet(
//               context: context,
//               backgroundColor: Colors.transparent,
//               builder: (BuildContext context) => ContentDetailBottomSheet(
//                 title: keys[index],
//                 imagePath: values[index],
//                 additionalText: '서버에서 받아올 정보',
//               ),
//             ),
//             child: GridTile(
//               child: ClipRRect(
//                 borderRadius: BorderRadius.all(Radius.circular(5.0)),
//                 child: Column(
//                   children: [
//                     Expanded(
//                       child: Image.asset(values[index], fit: BoxFit.cover),
//                     ),
//                     Container(
//                       padding: EdgeInsets.all(8),
//                       child: Text(
//                         keys[index],
//                         style: TextStyle(
//                           color: mainBlack,
//                           fontWeight: FontWeight.bold,
//                         ),
//                       ),
//                     ),
//                   ],
//                 ),
//               ),
//             ),
//           );
//         },
//       ),
//     );
//   }
// }
