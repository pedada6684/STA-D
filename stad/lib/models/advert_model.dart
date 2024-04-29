class Advert {
  final String title;
  final String description;
  final String bannerImgUrl;

  Advert({required this.title, required this.description, required this.bannerImgUrl});

  factory Advert.fromJson(Map<String, dynamic> json) {
    return Advert(
      title: json['title'] as String,
      description: json['description'] as String,
      bannerImgUrl: json['bannerImgUrl'] as String,
    );
  }
}
