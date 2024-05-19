class Review {
  final String? imageUrl;
  final String productName;
  final String productOption;
  final String title;
  final String reviewDate;
  final double rating;
  final String reviewContent;

  Review({
    this.imageUrl,
    required this.productName,
    required this.productOption,
    required this.title,
    required this.reviewDate,
    required this.rating,
    required this.reviewContent,
  });

  factory Review.fromJson(Map<String, dynamic> json) {
    return Review(
      imageUrl: json['imageUrl'] as String?,
      productName: json['productName'] as String,
      productOption: json['productOption'] as String,
      title: json['title'] as String,
      reviewDate: json['reviewDate'] as String,
      rating: (json['rating'] as num).toDouble() ?? 0.0,
      reviewContent: json['reviewContent'] as String,
    );
  }
}
