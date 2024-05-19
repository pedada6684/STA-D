class CartItem {
  final String id; //product Id
  final String cartProductId;
  final String title;
  final String thumbnail;
  final int price;
  int quantity;
  bool isSelected;
  final CartProductOption? option;
  final int advertId;
  final int contentId;

  CartItem({
    required this.id, //product Id
    required this.cartProductId,
    required this.title,
    required this.price,
    required this.thumbnail,
    required this.advertId,
    required this.contentId,
    this.quantity = 1,
    this.isSelected = false,
    this.option,
  });

  // JSON에서 CartItem 객체를 생성하는 factory 생성자
  factory CartItem.fromJson(Map<String, dynamic> json) {
    return CartItem(
      id: json['productId'].toString(),
      cartProductId: json['cartProductId'].toString(),
      title: json['productType']['name'],
      thumbnail: json['thumbnail'],
      price: json['productType']['price'] as int,
      quantity: json['quantity'] as int,
      option: json['option'] != null
          ? CartProductOption.fromJson(json['option'])
          : null,
      advertId: json['advertId'] as int, // 추가
      contentId: json['contentId'] as int, // 추가
    );
  }

  // CartItem 객체를 JSON으로 변환하는 메서드
  Map<String, dynamic> toJson() {
    return {
      'productId': id,
      'cartProductId': cartProductId,
      'quantity': quantity,
      'thumbnail': thumbnail,
      'title': title,
      'price': price,
      'option': option?.toJson(),
      'advertId': advertId, // 추가
      'contentId': contentId, // 추가
    };
  }

  void toggleSelection() {
    isSelected = !isSelected;
  }
}

class CartProductOption {
  final int id;
  final String name;
  final int value;

  CartProductOption({
    required this.id,
    required this.name,
    required this.value,
  });

  factory CartProductOption.fromJson(Map<String, dynamic> json) {
    return CartProductOption(
      id: json['id'] as int,
      name: json['name'] as String,
      value: json['value'] as int,
    );
  }

  Map<String, dynamic> toJson() => {
        'id': id,
        'name': name,
        'value': value,
      };
}

class CartProduct {
  final int userId;
  final List<CartProductDetail> cartProductList;

  CartProduct({required this.userId, required this.cartProductList});

  Map<String, dynamic> toJson() => {
        'userId': userId,
        'cartProductList':
            cartProductList.map((item) => item.toJson()).toList(),
      };
}

class CartProductDetail {
  final int productTypeId;
  final int quantity;
  final int advertId;
  final int contentId;
  final int optionId;

  CartProductDetail({
    required this.productTypeId,
    required this.quantity,
    required this.advertId,
    required this.contentId,
    required this.optionId,
  });

  factory CartProductDetail.fromJson(Map<String, dynamic> json) {
    return CartProductDetail(
      productTypeId: json['productTypeId'] as int,
      quantity: json['quantity'] as int,
      advertId: json['advertId'] as int,
      contentId: json['contentId'] as int,
      optionId: json['optionId'] as int,
    );
  }

  Map<String, dynamic> toJson() => {
        'productTypeId': productTypeId,
        'quantity': quantity,
        'advertId': advertId,
        'contentId': contentId,
        'optionId': optionId,
      };
}
