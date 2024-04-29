class Order {
  final int orderId;
  final String orderDate;
  final String orderStatus;
  final int contentId;
  final int advertId;
  final String deliveryStatus;
  final int productId;
  final String productName;
  final String productImgUrl;

  Order({
    required this.orderId,
    required this.orderDate,
    required this.orderStatus,
    required this.contentId,
    required this.advertId,
    required this.deliveryStatus,
    required this.productId,
    required this.productName,
    required this.productImgUrl,
  });

  factory Order.fromJson(Map<String, dynamic> json) {
    return Order(
      orderId: json['orderId'] as int,
      orderDate: json['orderDate'] as String,
      orderStatus: json['orderStatus'] as String,
      contentId: json['contentId'] as int,
      advertId: json['advertId'] as int,
      deliveryStatus: json['deliveryStatus'] as String,
      productId: json['productId'] as int,
      productName: json['productName'] as String,
      productImgUrl: json['productImgUrl'] as String,
    );
  }
}
