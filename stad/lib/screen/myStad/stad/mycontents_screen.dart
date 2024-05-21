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
    print(watchedContents);
    return Scaffold(
      backgroundColor: mainWhite,
      appBar: CustomAppBar(
        title: '내가 본 콘텐츠',
        showBackButton: true,
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
                  ),
                  itemCount: watchedContents.length,
                  itemBuilder: (context, index) {
                    final content = watchedContents[index];
                    return InkWell(
                      onTap: () => showModalBottomSheet(
                        context: context,
                        isScrollControlled: true,
                        builder: (BuildContext context) =>
                            ContentDetailBottomSheet(
                          title: content['title'],
                          imagePath: content['thumbnailUrl'],
                          additionalText: '서버에서 받아올 정보',
                          detailId: content['detailId'],
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
