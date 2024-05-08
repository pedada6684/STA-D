import 'package:flutter/material.dart';
import 'package:carousel_slider/carousel_slider.dart';
import 'package:stad/constant/colors.dart';
import 'package:stad/models/advert_model.dart';
import 'package:stad/models/contents_model.dart';
import 'package:stad/screen/product/product_screen.dart';
import 'package:stad/services/advert_service.dart';
import 'package:stad/services/contents_service.dart';
import 'package:stad/widget/advertising_card.dart';
import 'package:stad/widget/content_bottom_sheet.dart';

class HomeScreen extends StatefulWidget {
  const HomeScreen({super.key});

  @override
  State<HomeScreen> createState() => _HomeScreenState();
}

//TODO:백 SSE contentId, advertId 받아오기
class _HomeScreenState extends State<HomeScreen> with WidgetsBindingObserver {
  bool _isActive = true;
  int _current = 0;
  final CarouselController _controller = CarouselController();
  Content? featuredContent;
  List<Advert> adverts = [];
  List<Map<String, dynamic>> singleAdverts = [];

  @override
  void initState() {
    super.initState();
    WidgetsBinding.instance.addObserver(this);
    fetchFeaturedContent();
    fetchAdverts();
    fetchSingleAdvert(1);
  }

  void fetchSingleAdvert(int advertId) async {
    AdService adService = AdService();
    try {
      Map<String, dynamic> advertData = await adService.getAdInfo(advertId);
      setState(() {
        singleAdverts.add({
          'bannerImgUrl': advertData['bannerImgUrl'],
          'title': advertData['title'],
          'description': advertData['description'],
        });
      });
    } catch (e) {
      print('Failed to fetch single advert: $e');
    }
  }

  void fetchAdverts() async {
    AdService adService = AdService();
    try {
      List<Advert> fetchedAdverts =
          await adService.getAdvertsByContentId(1); // 컨텐츠 ID를 적절히 설정
      setState(() {
        adverts = fetchedAdverts;
      });
    } catch (e) {
      print('Failed to fetch adverts: $e');
    }
  }

  void fetchFeaturedContent() async {
    ContentsService contentsService = ContentsService();
    try {
      var fetchedContent = await contentsService
          .fetchContentDetails(1); // assuming 1 is a valid ID
      setState(() {
        featuredContent = fetchedContent;
      });
    } catch (e) {
      print('Failed to fetch content: $e');
    }
  }

  @override
  void dispose() {
    WidgetsBinding.instance.removeObserver(this);
    super.dispose();
  }

  @override
  void didChangeAppLifecycleState(AppLifecycleState state) {
    if (state == AppLifecycleState.resumed) {
      // 앱이 다시 활성화되었을 때
      setState(() {
        _isActive = true;
      });
    } else if (state == AppLifecycleState.paused) {
      // 앱이 비활성화되었을 때
      setState(() {
        _isActive = false;
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: mainWhite,
      appBar: PreferredSize(
        preferredSize: Size.fromHeight(50.0), // 앱바 높이 설정
        child: AppBar(
          scrolledUnderElevation: 0,
          backgroundColor: Colors.transparent,
          // AppBar 배경을 투명하게 설정
          elevation: 0,
          // 그림자 제거
          title: Text(
            'STA:D',
            style: TextStyle(
              color: mainWhite,
              fontSize: 35,
              fontWeight: FontWeight.bold,
              fontFamily: 'LogoFont',
              shadows: <Shadow>[
                Shadow(
                  offset: Offset(0.0, 1.0),
                  blurRadius: 3.0,
                  color: Color.fromARGB(150, 0, 0, 0),
                ),
              ],
            ),
          ),
          centerTitle: true,
        ),
      ),
      extendBodyBehindAppBar: true, // 바디를 앱바 뒤로 확장
      body: SingleChildScrollView(
        child: Column(
          children: [
            GestureDetector(
              onTap: () {
                if (featuredContent != null) {
                  showModalBottomSheet(
                    context: context,
                    isScrollControlled: true,
                    builder: (BuildContext context) => ContentDetailBottomSheet(
                      title: featuredContent!.title,
                      imagePath: featuredContent!.thumbnailUrl,
                      synopsis: featuredContent!.description,
                      seasonInfo:
                          '${featuredContent!.releaseYear} | ${featuredContent!.audienceAge}',
                      additionalText:
                          '${featuredContent!.creator} | ${featuredContent!.cast}',
                    ),
                  );
                }
              },
              child: buildTopThumbnail(featuredContent: featuredContent),
            ),
            Column(
              children: [
                if (singleAdverts.isNotEmpty)
                  AdvertisingCard(
                    bannerImgUrl: singleAdverts[0]['bannerImgUrl'],
                    buttonText: '지금 보는 광고가 궁금하다면?',
                    subText: singleAdverts[0]['title'],
                    onPressed: () {
                      //TODO: advertId 수정
                      Navigator.of(context).push(MaterialPageRoute(
                          //TODO 수정
                          builder: (context) => ProductScreen(
                                advertId: 1,
                                contentId: 1,
                                title: singleAdverts[0]['title'],
                                description: singleAdverts[0]['description'],
                              )));

                    },
                  ),
                buildCarouselSlider(context),
              ],
            ),
          ],
        ),
      ),
    );
  }

  CarouselSlider buildCarouselSlider(BuildContext context) {
    return CarouselSlider(
      items: adverts
          .map((advert) => AdvertisingCard(
                bannerImgUrl: advert.bannerImgUrl,
                buttonText: '콘텐츠 관련 광고 보러가기',
                subText: advert.title,
                onPressed: () {
                  Navigator.of(context).push(
                      //TODO:advertId 수정, 여러가지 수정
                      MaterialPageRoute(
                          builder: (context) => ProductScreen(
                                advertId: 1,
                                contentId: 1,
                                title: singleAdverts[0]['title'],
                                description: singleAdverts[0]['description'],
                              )));
                },
              ))
          .toList(),
      options: CarouselOptions(
        autoPlay: true,
        autoPlayInterval: Duration(seconds: 3),
        autoPlayAnimationDuration: Duration(milliseconds: 800),
        enableInfiniteScroll: true,
        aspectRatio: 16 / 9,
        viewportFraction: 1,
        enlargeCenterPage: true,
        scrollDirection: Axis.horizontal,
        onPageChanged: (index, reason) {
          setState(() {
            _current = index;
          });
        },
      ),
      carouselController: _controller,
    );
  }

// CarouselSlider buildCarouselSlider(BuildContext context) {
//   return CarouselSlider(
//               items: [
//                 AdvertisingCard(
//                   bannerImgUrl: 'assets/image/product.png',
//                   buttonText: '지금 보는 광고가 궁금하다면?',
//                   onPressed: () {
//                     Navigator.of(context).push(MaterialPageRoute(
//                         builder: (context) => ProductScreen()));
//                   },
//                 ),
//                 AdvertisingCard(
//                   bannerImgUrl: 'assets/image/product2.png',
//                   buttonText: '콘텐츠 관련 광고 구매하기',
//                   onPressed: () {
//                     Navigator.of(context).push(MaterialPageRoute(
//                         builder: (context) => ProductScreen()));
//                   },
//                 ),
//               ],
//               options: CarouselOptions(
//                   autoPlay: true,
//                   autoPlayInterval: Duration(seconds: 3),
//                   autoPlayAnimationDuration: Duration(milliseconds: 800),
//                   enableInfiniteScroll: true,
//                   aspectRatio: 16 / 9,
//                   viewportFraction: 1,
//                   enlargeCenterPage: true,
//                   scrollDirection: Axis.horizontal,
//                   onPageChanged: (index, reason) {
//                     setState(() {
//                       _current = index;
//                     });
//                   }),
//               carouselController: _controller,
//             );
// }
}

class buildTopThumbnail extends StatelessWidget {
  const buildTopThumbnail({
    super.key,
    required this.featuredContent,
  });

  final Content? featuredContent;

  @override
  Widget build(BuildContext context) {
    return Stack(
      children: [
        ShaderMask(
          shaderCallback: (rect) {
            return LinearGradient(
              begin: Alignment.topCenter,
              end: Alignment.bottomCenter,
              colors: [mainBlack, Colors.transparent],
              stops: [0.4, 1.0],
            ).createShader(Rect.fromLTRB(0, 0, rect.width, rect.height));
          },
          blendMode: BlendMode.dstIn,
          child: featuredContent == null
              ? Center(
                  child:
                      CircularProgressIndicator()) // Show loading spinner if content is null
              : Image.network(
                  featuredContent!.thumbnailUrl,
                  height: 420,
                  width: double.infinity,
                  fit: BoxFit.cover,
                ),
        ),
        Positioned(
          top: 0,
          left: 0,
          right: 0,
          child: Container(
            height: 100,
            decoration: BoxDecoration(
              gradient: LinearGradient(
                begin: Alignment.topCenter,
                end: Alignment.bottomCenter,
                colors: [mainBlack.withOpacity(0.2), Colors.transparent],
                stops: [0.0, 0.5],
              ),
            ),
          ),
        ),
        Positioned(
          bottom: 35,
          left: 25,
          child: Text(
            '지금 보는 컨텐츠',
            style: TextStyle(
              fontSize: 18,
              fontWeight: FontWeight.bold,
              color: mainWhite,
              shadows: <Shadow>[
                Shadow(
                  offset: Offset(0.0, 0.8),
                  blurRadius: 3.0,
                  color: Color.fromARGB(150, 0, 0, 0),
                ),
              ],
            ),
          ),
        ),
      ],
    );
  }
}
