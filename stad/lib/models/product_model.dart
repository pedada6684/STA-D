// ProductInfo.dart
class ProductInfo {
  final int id;
  final String name;
  final List<ProductImage> images;
  final String thumbnail;
  final int cityDeliveryFee;
  final int mtDeliveryFee;
  final DateTime expStart;
  final DateTime expEnd;

  ProductInfo({
    required this.id,
    required this.name,
    required this.images,
    required this.thumbnail,
    required this.cityDeliveryFee,
    required this.mtDeliveryFee,
    required this.expStart,
    required this.expEnd,
  });

  factory ProductInfo.fromJson(Map<String, dynamic> json) {
    if (json['id'] == null ||
        json['name'] == null ||
        json['images'] == null ||
        json['thumbnail'] == null ||
        json['cityDeliveryFee'] == null ||
        json['mtDeliveryFee'] == null ||
        json['expStart'] == null ||
        json['expEnd'] == null) {
      throw Exception('One or more required fields are missing or null');
    }
    return ProductInfo(
      id: json['id'] ?? 0,
      name: json['name'] ?? '광고상품',
      images: (json['images'] as List<dynamic>? ?? []).map((i) =>
          ProductImage.fromJson(i)).toList(),
      thumbnail: json['thumbnail'] ?? 'default_thumbnail.png',
      cityDeliveryFee: json['cityDeliveryFee'] ?? 0,
      mtDeliveryFee: json['mtDeliveryFee'] ?? 0,
      expStart: DateTime.parse(json['expStart'] ?? DateTime.now().toString()),
      expEnd: DateTime.parse(
          json['expEnd'] ?? DateTime.now().add(Duration(days: 365)).toString()),
    );
  }
}

class ProductImage {
  final String imageUrl;

  ProductImage({required this.imageUrl});

  factory ProductImage.fromJson(dynamic json) {
    return ProductImage(
      imageUrl: json as String, // 직접 String을 받아서 처리
    );
  }
}


// ProductType.dart
class ProductType {
  final int id;
  final String name;
  final int price;
  int quantity;
  final List<ProductOption> productOptions; // Ensure this is included

  ProductType({
    required this.id,
    required this.name,
    required this.price,
    this.quantity = 1,
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

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'name': name,
      'price': price,
      'quantity': quantity,
      'productOptions':
          productOptions.map((option) => option.toJson()).toList(),
    };
  }
}

class ProductOption {
  final String name;
  final int value;

  ProductOption({
    required this.name,
    required this.value,
  });

  factory ProductOption.fromJson(Map<String, dynamic> json) {
    return ProductOption(
      name: json['name'] as String,
      value: json['value'] as int,
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'name': name,
      'value': value,
    };
  }
}
