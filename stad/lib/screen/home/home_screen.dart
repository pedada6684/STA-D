import 'package:carousel_slider/carousel_slider.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:stad/constant/colors.dart';
import 'package:stad/models/advert_model.dart';
import 'package:stad/models/contents_model.dart';
import 'package:stad/providers/contents_provider.dart';
import 'package:stad/providers/user_provider.dart';
import 'package:stad/screen/product/product_screen.dart';
import 'package:stad/services/advert_service.dart';
import 'package:stad/services/alert_service.dart';
import 'package:stad/services/contents_service.dart';
import 'package:stad/services/log_service.dart';
import 'package:stad/widget/advertising_card.dart';
import 'package:stad/widget/content_bottom_sheet.dart';

class HomeScreen extends StatefulWidget {
  const HomeScreen({super.key});

  @override
  State<HomeScreen> createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> with WidgetsBindingObserver {
  bool _isActive = true;
  int _current = 0;
  final CarouselController _controller = CarouselController();
  List<Advert> adverts = [];
  List<Map<String, dynamic>> popularAds = [];
  AlertService alertService = AlertService();

  @override
  void initState() {
    super.initState();
    WidgetsBinding.instance.addObserver(this);
    int? userId = Provider.of<UserProvider>(context, listen: false).userId;
    if (userId != null) {
      var contentProvider = Provider.of<ContentProvider>(context, listen: false);
      alertService.connectToSSE(userId.toString(), contentProvider);
      fetchAdverts();
      fetchPopularAds();
    }
  }

  void fetchAdverts() async {
    AdService adService = AdService();
    try {
      List<Advert> fetchedAdverts = await adService.getAdvertsByContentId(1);
      setState(() {
        adverts = fetchedAdverts;
      });
    } catch (e) {
      print('Failed to fetch adverts: $e');
    }
  }

  void fetchPopularAds() async {
    AdService adService = AdService();
    try {
      List<Map<String, dynamic>> popularAdData =
      await adService.getPopularAdInfo();
      setState(() {
        popularAds = popularAdData;
      });
    } catch (e) {
      print('Failed to fetch popular ads: $e');
    }
  }

  void advertClickLog(
      int advertId, int advertVideoId, int contentId, int userId) async {
    LogService logService = LogService();
    try {
      await logService.advertClickLog(
        advertId: advertId,
        advertVideoId: advertVideoId,
        contentId: contentId,
        userId: userId,
      );
    } catch (e) {
      print('Failed to log advert click: $e');
    }
  }

  @override
  void dispose() {
    WidgetsBinding.instance.removeObserver(this);
    alertService.disconnect();
    super.dispose();
  }

  @override
  void didChangeAppLifecycleState(AppLifecycleState state) {
    if (state == AppLifecycleState.resumed) {
      setState(() {
        _isActive = true;
      });
    } else if (state == AppLifecycleState.paused) {
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
        preferredSize: Size.fromHeight(50.0),
        child: AppBar(
          scrolledUnderElevation: 0,
          backgroundColor: Colors.transparent,
          elevation: 0,
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
      extendBodyBehindAppBar: true,
      body: Consumer<ContentProvider>(
        builder: (context, contentProvider, child) {
          final featuredContent = contentProvider.featuredContent;
          final popularContents = contentProvider.popularContents;

          return SingleChildScrollView(
            child: Column(
              children: [
                if (featuredContent != null)
                  GestureDetector(
                    onTap: () {
                      if (featuredContent != null) {
                        showModalBottomSheet(
                          context: context,
                          isScrollControlled: true,
                          builder: (BuildContext context) =>
                              ContentDetailBottomSheet(
                                title: featuredContent.title,
                                imagePath: featuredContent.thumbnailUrl,
                                synopsis: featuredContent.description,
                                seasonInfo:
                                '${featuredContent.releaseYear} | ${featuredContent.audienceAge} | ${featuredContent.playtime}',
                                additionalText:
                                '크리에이터 : ${featuredContent.creator}\n출연 : ${featuredContent.cast}',
                              ),
                        );
                      }
                    },
                    child: buildTopThumbnail(featuredContent: featuredContent),
                  )
                else if (popularContents.isNotEmpty)
                  buildPopularContentCarousel(popularContents)
                else
                  Center(child: CircularProgressIndicator()),
                SizedBox(
                  height: 20.0,
                ),
                Column(
                  children: [
                    if (adverts.isNotEmpty)
                      buildCarouselSlider(context)
                    else if (popularAds.isNotEmpty)
                      buildPopularAdsCarousel(context)
                    else
                      Center(child: Text('No ads available.')),
                  ],
                ),
                SizedBox(
                  height: 20.0,
                ),
              ],
            ),
          );
        },
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
          Navigator.of(context).push(MaterialPageRoute(
              builder: (context) => ProductScreen(
                advertId: advert.advertId,
                contentId: 1,
                title: advert.title,
                description: advert.description,
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

  Widget buildPopularAdsCarousel(BuildContext context) {
    return CarouselSlider(
      items: popularAds.map((ad) {
        return GestureDetector(
          onTap: () {
            Navigator.of(context).push(MaterialPageRoute(
              builder: (context) => ProductScreen(
                advertId: ad['advertId'],
                contentId: 1,
                title: ad['title'],
                description: ad['description'],
              ),
            ));
          },
          child: AdvertisingCard(
            bannerImgUrl: ad['bannerImgUrl'],
            buttonText: '인기 광고 보러가기',
            subText: ad['title'],
            onPressed: () {
              Navigator.of(context).push(MaterialPageRoute(
                builder: (context) => ProductScreen(
                  advertId: ad['advertId'],
                  contentId: -1,
                  title: ad['title'],
                  description: ad['description'],
                ),
              ));
            },
          ),
        );
      }).toList(),
      options: CarouselOptions(
        height: 380.0,
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

  Widget buildPopularContentCarousel(List<Map<String, dynamic>> popularContents) {
    return CarouselSlider(
      items: popularContents.map((content) {
        return GestureDetector(
          onTap: () {
            showModalBottomSheet(
              context: context,
              isScrollControlled: true,
              builder: (BuildContext context) => ContentDetailBottomSheet(
                title: '${content['title']} | ${content['episode']}',
                imagePath: content['thumbnailUrl'],
                synopsis: content['description'],
                seasonInfo:
                '${content['releaseYear']} | ${content['audienceAge']} | ${content['playtime']}',
                additionalText:
                '크리에이터 : ${content['creator']}\n출연 : ${content['cast']}',
              ),
            );
          },
          child: buildPopularContentThumbnail(content),
        );
      }).toList(),
      options: CarouselOptions(
        height: 500.0,
        autoPlay: true,
        autoPlayInterval: Duration(seconds: 3),
        autoPlayAnimationDuration: Duration(milliseconds: 800),
        enableInfiniteScroll: true,
        aspectRatio: 16 / 9,
        viewportFraction: 1,
        enlargeCenterPage: true,
        scrollDirection: Axis.horizontal,
      ),
      carouselController: _controller,
    );
  }

  Widget buildPopularContentThumbnail(Map<String, dynamic> content) {
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
          child: Image.network(
            content['thumbnailUrl'],
            height: MediaQuery.of(context).size.height * 0.55,
            width: double.infinity,
            fit: BoxFit.fill,
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
            '현재 인기 콘텐츠',
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
              ? Center(child: CircularProgressIndicator())
              : Image.network(
            featuredContent!.thumbnailUrl,
            height: 500,
            width: double.infinity,
            fit: BoxFit.fill,
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
