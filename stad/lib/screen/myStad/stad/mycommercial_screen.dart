import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:stad/constant/colors.dart';
import 'package:stad/models/advert_model.dart';
import 'package:stad/providers/user_provider.dart';
import 'package:stad/screen/product/product_screen.dart';
import 'package:stad/services/ad_service.dart';
import 'package:stad/widget/advertising_card.dart';
import 'package:stad/widget/app_bar.dart';

class MyCommercialScreen extends StatefulWidget {
  const MyCommercialScreen({super.key});

  @override
  State<MyCommercialScreen> createState() => _MyCommercialScreenState();
}

class _MyCommercialScreenState extends State<MyCommercialScreen> {
  List<Advert> adverts = [];
  bool isLoading = true;

  @override
  void initState() {
    super.initState();
    fetchAdverts();
  }

  void fetchAdverts() async {
    try {
      int userId =
          Provider.of<UserProvider>(context, listen: false).userId ?? 0;
      adverts = await AdService().fetchAdverts(userId);
      setState(() => isLoading = false);
    } catch (e) {
      print('광고 목록 받아오기 실패: $e');
      setState(() => isLoading = false);
    } finally {
      setState(() => isLoading = false);
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: mainWhite,
      appBar: CustomAppBar(
        title: '내가 본 광고',
        showBackButton: true,
      ),
      // body: Padding(
      //   padding: const EdgeInsets.all(16.0),
      //   child: Column(
      //     children: [
      //
      //       AdvertisingCard(
      //         imagePath: 'assets/image/advertising.png',
      //         buttonText: '민형이가 조아하는 딸기',
      //         onPressed: () {
      //           Navigator.push(
      //             context,
      //             CupertinoPageRoute(
      //               builder: (context) => ProductScreen(),
      //             ),
      //           );
      //         },
      //       ),
      //       AdvertisingCard(
      //         imagePath: 'assets/image/advertising.png',
      //         buttonText: '민형이가 조아하는 딸기',
      //         onPressed: () {
      //           Navigator.push(
      //             context,
      //             CupertinoPageRoute(
      //               builder: (context) => ProductScreen(),
      //             ),
      //           );
      //         },
      //       ),
      //     ],
      //   ),
      // ),
      body: isLoading
          ? Center(child: CircularProgressIndicator())
          : adverts.isEmpty
              ? Center(
                  child: Text('내가 본 광고가 없습니다.',
                      style: TextStyle(fontSize: 18, color: darkGray)))
              : ListView.builder(
                  itemCount: adverts.length,
                  itemBuilder: (context, index) {
                    final advert = adverts[index];
                    return AdvertisingCard(
                      bannerImgUrl:
                          advert.bannerImgUrl ?? 'assets/image/default.png',
                      // Handle possible null URL
                      buttonText: '내가 본 광고',
                      onPressed: () {
                        Navigator.push(
                          context,
                          MaterialPageRoute(
                              builder: (context) => ProductScreen(advertId :1)),
                        );
                      }, subText: advert.title,
                    );
                  },
                ),
    );
  }
}
