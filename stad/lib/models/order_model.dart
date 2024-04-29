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
