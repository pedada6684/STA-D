class Content {
  final String title;
  final String thumbnailUrl;
  final String playtime;
  final String releaseYear;
  final String audienceAge;
  final String creator;
  final String cast;
  final String description;

  Content({
    required this.title,
    required this.thumbnailUrl,
    required this.playtime,
    required this.releaseYear,
    required this.audienceAge,
    required this.creator,
    required this.cast,
    required this.description,
  });

  factory Content.fromJson(Map<String, dynamic> json) {
    return Content(
      title: json['title'] ?? '',
      thumbnailUrl: json['thumbnailUrl'] ?? '',
      playtime: json['playtime'] ?? '',
      releaseYear: json['releaseYear'] ?? '',
      audienceAge: json['audienceAge'] ?? '',
      creator: json['creator'] ?? '',
      cast: json['cast'] ?? '',
      description: json['description'] ?? '',
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'title': title,
      'thumbnailUrl': thumbnailUrl,
      'playtime': playtime,
      'releaseYear': releaseYear,
      'audienceAge': audienceAge,
      'creator': creator,
      'cast': cast,
      'description': description,
    };
  }
}
