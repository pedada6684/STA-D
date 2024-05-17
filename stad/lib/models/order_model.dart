class Order {
  final int orderId;
  final String orderDate;
  final String orderStatus;
  final int contentId;
  final int advertId;
  final List<int> productTypeId;
  final List<String> productTypeName;
  final List<String> productTypeThumbnailUrl;

  Order({
    required this.orderId,
    required this.orderDate,
    required this.orderStatus,
    required this.contentId,
    required this.advertId,
    required this.productTypeId,
    required this.productTypeName,
    required this.productTypeThumbnailUrl,
  });

  factory Order.fromJson(Map<String, dynamic> json) {
    return Order(
      orderId: json['ordersId'] as int,
      orderDate: json['orderDate'] as String,
      orderStatus: json['orderStatus'] as String,
      contentId: json['contentId'] as int,
      advertId: json['advertId'] as int,
      productTypeId: List<int>.from(json['productTypeId']),
      productTypeName: List<String>.from(json['productTypeName']),
      productTypeThumbnailUrl: List<String>.from(json['productTypeThumbnailUrl']),
    );
  }
}

class OrderDetails {
  final int orderId;
  final String orderDate;
  final String orderStatus;
  final List<ProductDetails> productDetails;

  OrderDetails({
    required this.orderId,
    required this.orderDate,
    required this.orderStatus,
    required this.productDetails,
  });

  factory OrderDetails.fromJson(Map<String, dynamic> json) {
    var list = json['productTypes'] as List;
    List<ProductDetails> productDetailsList = list.map((i) => ProductDetails.fromJson(i)).toList();

    return OrderDetails(
      orderId: json['ordersId'] as int,
      orderDate: json['orderDate'] as String,
      orderStatus: json['orderStatus'] as String,
      productDetails: productDetailsList,
    );
  }
}

class ProductDetails {
  final int productTypeId;
  final String productName;
  final int productCnt;
  final int productPrice;
  final String productImg;
  final int? optionId;
  final String? optionName;
  final int? optionValue;

  ProductDetails({
    required this.productTypeId,
    required this.productName,
    required this.productCnt,
    required this.productPrice,
    required this.productImg,
    this.optionId,
    this.optionName,
    this.optionValue,
  });

  factory ProductDetails.fromJson(Map<String, dynamic> json) {
    return ProductDetails(
      productTypeId: json['productTypeId'] as int,
      productName: json['productName'] as String,
      productCnt: json['productCnt'] as int,
      productPrice: (json['productPrice'] as int),
      productImg: json['productImg'] as String,
      optionId: json['optionId'] as int?,
      optionName: json['optionName'] as String?,
      optionValue: json['optionValue'] as int?,
    );
  }
}
