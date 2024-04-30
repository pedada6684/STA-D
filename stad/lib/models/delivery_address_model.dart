class DeliveryAddress {
  // final int userId;
  final int id;
  final String name;
  final String phone;
  final String location;
  final String locationNick;

  DeliveryAddress({
    // required this.userId,
    required this.id,
    required this.name,
    required this.phone,
    required this.location,
    required this.locationNick,
  });

  factory DeliveryAddress.fromJson(Map<String, dynamic> json) {
    return DeliveryAddress(
      // userId: json['userId'] as int, // null safety 적용
      id: json['id'] as int, // null safety 적용
      name: json['name'] as String,
      phone: json['phone'] as String,
      location: json['location'] as String,
      locationNick: json['locationNick'] as String,
    );
  }

  Map<String, dynamic> toJson() {
    return {
      // 'userId': userId,
      'id': id,
      'name': name,
      'phone': phone,
      'location': location,
      'locationNick': locationNick,
    };
  }
}
