class Advert {
  final int advertId;
  final String title;
  final String description;
  final String startDate;
  final String endDate;
  final String type;
  final String directVideoUrl;
  final String bannerImgUrl;
  final List<int> selectedContentList;
  final List<String> advertVideoUrlList;
  final String category;

  Advert({
    required this.advertId,
    required this.title,
    required this.description,
    required this.startDate,
    required this.endDate,
    required this.type,
    required this.directVideoUrl,
    required this.bannerImgUrl,
    required this.selectedContentList,
    required this.advertVideoUrlList,
    required this.category,
  });

  factory Advert.fromJson(Map<String, dynamic> json) {
    return Advert(
      advertId: json['advertId'] as int,
      title: json['title'] ?? 'Default title', // Null 대체
      description: json['description'] ?? 'No description provided',
      startDate: json['startDate'] ?? 'Unknown',
      endDate: json['endDate'] ?? 'Unknown',
      type: json['type'] ?? 'No type',
      directVideoUrl: json['directVideoUrl'] ?? 'No URL',
      bannerImgUrl: json['bannerImgUrl'] ?? 'assets/default.png',
      selectedContentList: (json['selectedContentList'] as List<dynamic>?)?.map((id) => id as int).toList() ?? [],
      advertVideoUrlList: (json['advertVideoUrlList'] as List<dynamic>?)?.map((url) => url as String).toList() ?? [],
      category: json['category'] ?? 'Uncategorized',
    );
  }
}
