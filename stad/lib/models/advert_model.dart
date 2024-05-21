class Advert {
  final int advertId;
  final String title;
  final String description;
  final String startDate;
  final String endDate;
  final String type;
  final String directVideoUrl;
  final String bannerImgUrl;

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
    required this.category,
  });

  factory Advert.fromJson(Map<String, dynamic> json) {
    return Advert(
      advertId: json['advertId'] as int,
      title: json['title'] ?? 'Default title',
      description: json['description'] ?? 'No description provided',
      startDate: json['startDate'] ?? 'Unknown',
      endDate: json['endDate'] ?? 'Unknown',
      type: json['type'] ?? 'No type',
      directVideoUrl: json['directVideoUrl'] ?? 'No URL',
      bannerImgUrl: json['bannerImgUrl'] ?? 'assets/default.png',
      category: json['advertCategory'] ?? 'Uncategorized',
    );
  }

  Map<String, dynamic> toMap() {
    return {
      'advertId': advertId,
      'title': title,
      'description': description,
      'startDate': startDate,
      'endDate': endDate,
      'type': type,
      'directVideoUrl': directVideoUrl,
      'bannerImgUrl': bannerImgUrl,
      'category': category,
    };
  }
}
