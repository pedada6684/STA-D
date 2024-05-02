// ProductInfo.dart
class ProductInfo {
  final int id;
  final List<ProductImage> images;
  final String thumbnail;
  final int cityDeliveryFee;
  final int mtDeliveryFee;
  final DateTime expStart;
  final DateTime expEnd;

  ProductInfo({
    required this.id,
    required this.images,
    required this.thumbnail,
    required this.cityDeliveryFee,
    required this.mtDeliveryFee,
    required this.expStart,
    required this.expEnd,
  });

  factory ProductInfo.fromJson(Map<String, dynamic> json) {
    if (json['id'] == null ||
        json['images'] == null ||
        json['thumbnail'] == null ||
        json['cityDeliveryFee'] == null ||
        json['mtDeliveryFee'] == null ||
        json['expStart'] == null ||
        json['expEnd'] == null) {
      throw Exception('One or more required fields are missing or null');
    }
    return ProductInfo(
      id: json['id'] ?? 0,  // 기본값을 0으로 설정
      images: (json['images'] as List<dynamic>? ?? []).map((i) => ProductImage.fromJson(i)).toList(),
      thumbnail: json['thumbnail'] ?? 'default_thumbnail.png',  // 기본 썸네일 이미지
      cityDeliveryFee: json['cityDeliveryFee'] ?? 0,  // 기본 배송료
      mtDeliveryFee: json['mtDeliveryFee'] ?? 0,
      expStart: json['expStart'] != null ? DateTime.parse(json['expStart']) : DateTime.now(),  // 시작 기본값 오늘
      expEnd: json['expEnd'] != null ? DateTime.parse(json['expEnd']) : DateTime.now().add(Duration(days: 365)),  // 종료 기본값 1년 후
    );
  }
}

class ProductImage {
  final String imageUrl;

  ProductImage({required this.imageUrl});

  factory ProductImage.fromJson(Map<String, dynamic> json) {
    return ProductImage(
      imageUrl: json['url'] as String,
    );
  }
}

// ProductType.dart
class ProductType {
  final int id;
  final String name;
  final int price;
  final int quantity;
  final List<ProductOption> productOptions; // Ensure this is included

  ProductType({
    required this.id,
    required this.name,
    required this.price,
    required this.quantity,
    this.productOptions = const [],
  });

  factory ProductType.fromJson(Map<String, dynamic> json) {
    return ProductType(
      id: json['id'] as int,
      name: json['name'] as String,
      price: json['price'] as int,
      quantity: json['quantity'] as int,
      productOptions: (json['productOptions'] as List<dynamic>? ?? [])
          .map((item) => ProductOption.fromJson(item))
          .toList(),
    );
  }
}

class ProductOption {
  final String name;
  final int additionalCost;

  ProductOption({
    required this.name,
    required this.additionalCost,
  });

  factory ProductOption.fromJson(Map<String, dynamic> json) {
    return ProductOption(
      name: json['name'] as String,
      additionalCost: json['additionalCost'] as int,
    );
  }
}


