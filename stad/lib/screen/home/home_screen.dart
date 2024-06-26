import 'dart:ui';

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
import 'package:stad/services/log_service.dart';
import 'package:stad/widget/advertising_card.dart';
import 'package:stad/widget/app_bar.dart';
import 'package:stad/widget/content_bottom_sheet.dart';
import 'package:webview_flutter/webview_flutter.dart';

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
  List<Advert> popularAds = [];
  List<Advert> contentRelatedAds = [];
  AlertService alertService = AlertService();

  @override
  void initState() {
    super.initState();
    WidgetsBinding.instance.addObserver(this);
    int? userId = Provider.of<UserProvider>(context, listen: false).userId;
    if (userId != null) {
      var contentProvider =
      Provider.of<ContentProvider>(context, listen: false);
      contentProvider
          .fetchCurrentOrPopularContent(userId)
          .then((contentDetailId) {
        alertService.connectToSSE(userId.toString(), contentProvider);
        if (contentDetailId != null) {
          fetchAdverts(contentDetailId);
          fetchContentRelatedAds(contentDetailId);
        } else {
          fetchPopularAds();
        }
      });
    }
  }

  Future<void> fetchAdverts(int contentId) async {
    AdService adService = AdService();
    try {
      List<Advert> fetchedAdverts =
      await adService.getAdvertsByContentId(contentId);
      setState(() {
        adverts = fetchedAdverts;
      });
    } catch (e) {
      print('Failed to fetch adverts: $e');
    }
  }

  Future<void> fetchContentRelatedAds(int contentId) async {
    AdService adService = AdService();
    try {
      List<Advert> fetchedContentRelatedAds =
      await adService.getAdvertsByContentId(contentId);
      setState(() {
        contentRelatedAds = fetchedContentRelatedAds;
      });
    } catch (e) {
      print('Failed to fetch content related ads: $e');
    }
  }

  Future<void> fetchPopularAds() async {
    AdService adService = AdService();
    try {
      List<Advert> popularAdData = await adService.getPopularAdInfo();
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

  Future<void> _handleRefresh() async {
    int? userId = Provider.of<UserProvider>(context, listen: false).userId;
    if (userId != null) {
      var contentProvider =
      Provider.of<ContentProvider>(context, listen: false);
      contentProvider
          .fetchCurrentOrPopularContent(userId)
          .then((contentDetailId) {
        if (contentDetailId != null) {
          fetchAdverts(contentDetailId);
          fetchContentRelatedAds(contentDetailId);
        } else {
          fetchPopularAds();
        }
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    int? userId = Provider.of<UserProvider>(context, listen: false).userId;

    return Scaffold(
      backgroundColor: mainWhite,
      appBar: AppBar(
        centerTitle: true,
        backgroundColor: mainNavy,
        title: Text(
          'STA:D',
          style: TextStyle(
              color: mainWhite, fontFamily: 'LogoFont', fontSize: 32.0),
        ),
      ),
      body: RefreshIndicator(
        onRefresh: _handleRefresh,
        child: Consumer<ContentProvider>(
          builder: (context, contentProvider, child) {
            final featuredContent = contentProvider.featuredContent;
            final popularContents = contentProvider.popularContents;
            final adverts = contentProvider.adverts;
            final contentRelatedAdverts = contentProvider.contentRelatedAdverts;
            final currentContentId = contentProvider.currentContentId ?? -1;

            return SingleChildScrollView(
              physics: const AlwaysScrollableScrollPhysics(),
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
                                  detailId: currentContentId,
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
                  Column(
                    children: [
                      if (adverts.isNotEmpty)
                        buildCarouselSlider(context, adverts.cast<Advert>(),
                            currentContentId, userId!, '지금 보고 있는 광고가 궁금하다면?')
                      else if (popularAds.isNotEmpty)
                        buildPopularAdsCarousel(context, userId!)
                      else
                        Container(
                          height: 100.0,
                          child: const Center(
                            child: Text(
                              '관련된 광고가 없습니다.',
                              style: TextStyle(fontSize: 16.0, color: midGray),
                            ),
                          ),
                        ),
                      if (contentRelatedAdverts.isNotEmpty)
                        buildCarouselSlider(
                            context,
                            contentRelatedAdverts.cast<Advert>(),
                            currentContentId,
                            userId!,
                            '컨텐츠 관련 광고'),
                      buildImageCarousel(),
                    ],
                  ),
                ],
              ),
            );
          },
        ),
      ),
    );
  }

  CarouselSlider buildCarouselSlider(BuildContext context, List<Advert> adverts,
      int currentContentId, int userId, String title) {
    return CarouselSlider(
      items: adverts
          .map((advert) => AdvertisingCard(
        bannerImgUrl: advert.bannerImgUrl,
        buttonText: title,
        subText: advert.title,
        onPressed: () {
          print('지금 보는 광고의 id :$currentContentId');
          advertClickLog(advert.advertId, 0, currentContentId, userId);
          if (advert.directVideoUrl != 'No URL') {
            Navigator.of(context).push(MaterialPageRoute(
                builder: (context) => WebViewScreen(
                  title: advert.title,
                  url: advert.directVideoUrl!,
                )));
          } else {
            Navigator.of(context).push(MaterialPageRoute(
                builder: (context) => ProductScreen(
                  advertId: advert.advertId,
                  contentId: currentContentId,
                  title: advert.title,
                  description: advert.description,
                )));
          }
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

  Widget buildPopularAdsCarousel(BuildContext context, int userId) {
    return CarouselSlider(
      items: popularAds.map((ad) {
        return GestureDetector(
          onTap: () {
            advertClickLog(ad.advertId, 0, 0, userId);
            if (ad.directVideoUrl != 'No URL') {
              Navigator.of(context).push(MaterialPageRoute(
                  builder: (context) => WebViewScreen(
                    title: ad.title,
                    url: ad.directVideoUrl!,
                  )));
            } else {
              Navigator.of(context).push(MaterialPageRoute(
                builder: (context) => ProductScreen(
                  advertId: ad.advertId,
                  contentId: 0,
                  title: ad.title,
                  description: ad.description,
                ),
              ));
            }
          },
          child: AdvertisingCard(
            bannerImgUrl: ad.bannerImgUrl,
            buttonText: '인기 광고 보러가기',
            subText: ad.title,
            onPressed: () {
              advertClickLog(ad.advertId, 0, 0, userId);
              if (ad.directVideoUrl != 'No URL') {
                print(ad.directVideoUrl);
                Navigator.of(context).push(MaterialPageRoute(
                    builder: (context) => WebViewScreen(
                      title: ad.title,
                      url: ad.directVideoUrl!,
                    )));
              } else {
                print(ad.directVideoUrl);
                Navigator.of(context).push(MaterialPageRoute(
                  builder: (context) => ProductScreen(
                    advertId: ad.advertId,
                    contentId: -1,
                    title: ad.title,
                    description: ad.description,
                  ),
                ));
              }
            },
          ),
        );
      }).toList(),
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

  Widget buildPopularContentCarousel(
      List<Map<String, dynamic>> popularContents) {
    return CarouselSlider(
      items: popularContents.map((content) {
        return Stack(
          fit: StackFit.expand,
          children: [
            Image.network(
              content['thumbnailUrl'],
              fit: BoxFit.cover,
            ),
            BackdropFilter(
              filter: ImageFilter.blur(sigmaX: 5.0, sigmaY: 5.0),
              child: Container(
                color: Colors.black.withOpacity(0.2),
              ),
            ),
            GestureDetector(
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
                    detailId: content['detailId'],
                  ),
                );
              },
              child: buildPopularContentThumbnail(content),
            ),
            Positioned(
              top: 35,
              left: 25,
              child: Text(
                'STA:D 인기 컨텐츠',
                style: TextStyle(
                  fontSize: 22,
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
      }).toList(),
      options: CarouselOptions(
        height: MediaQuery.of(context).size.height * 0.4,
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
    return Padding(
      padding: const EdgeInsets.symmetric(horizontal: 4.0, vertical: 80.0),
      child: ClipRRect(
        borderRadius: BorderRadius.circular(10.0),
        child: Image.network(
          content['thumbnailUrl'],
          width: double.infinity,
          fit: BoxFit.contain,
        ),
      ),
    );
  }

  Widget buildImageCarousel() {
    return CarouselSlider(
      items: [
        'assets/image/banner1.png',
        'assets/image/banner2.png',
        'assets/image/banner3.png',
      ].map((imagePath) {
        return Padding(
          padding: const EdgeInsets.symmetric(vertical: 4.0, horizontal: 4.0),
          child: ClipRRect(
            borderRadius: BorderRadius.circular(10.0),
            child: Image.asset(
              imagePath,
              fit: BoxFit.cover,
              width: double.infinity,
            ),
          ),
        );
      }).toList(),
      options: CarouselOptions(
        autoPlay: true,
        autoPlayInterval: Duration(seconds: 5),
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
}

class buildTopThumbnail extends StatelessWidget {
  const buildTopThumbnail({
    super.key,
    required this.featuredContent,
  });

  final Content? featuredContent;

  @override
  Widget build(BuildContext context) {
    return Container(
      height: MediaQuery.of(context).size.height * 0.4,
      child: Stack(
        fit: StackFit.expand,
        children: [
          featuredContent == null
              ? Container(
            color: mainWhite,
            child: Center(
              child: CircularProgressIndicator(
                color: mainNavy,
              ),
            ),
          )
              : Image.network(
            featuredContent!.thumbnailUrl,
            fit: BoxFit.cover,
          ),
          BackdropFilter(
            filter: ImageFilter.blur(sigmaX: 5.0, sigmaY: 5.0),
            child: Container(
              color: Colors.black.withOpacity(0.2),
            ),
          ),
          featuredContent != null
              ? Center(
            child: Padding(
              padding: const EdgeInsets.symmetric(horizontal: 4.0, vertical: 80.0),
              child: ClipRRect(
                borderRadius: BorderRadius.circular(10.0),
                child: Image.network(
                  featuredContent!.thumbnailUrl,
                  width: double.infinity,
                  fit: BoxFit.contain,
                ),
              ),
            ),
          )
              : Container(),
          Positioned(
            top: 35,
            left: 25,
            child: Text(
              '지금 보는 컨텐츠',
              style: TextStyle(
                fontSize: 22,
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
      ),
    );
  }
}

class WebViewScreen extends StatelessWidget {
  final String url;
  final String title;

  WebViewScreen({required this.url, required this.title});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: CustomAppBar(
        title: title,
      ),
      body: WebView(
        initialUrl: url,
        javascriptMode: JavascriptMode.unrestricted,
      ),
    );
  }
}
