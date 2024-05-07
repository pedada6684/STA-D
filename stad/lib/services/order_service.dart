import 'package:dio/dio.dart';
import 'package:stad/constant/api.dart';
import 'package:stad/models/order_model.dart';

class OrderService {
  final Dio dio = Dio();

  // final String EmUrl = 'http://192.168.0.9:8080/api/user/location';
  // final String orderUrl = '$svApi/orders/list?userId=';
  final String orderUrl = 'http://192.168.0.9:8080/orders';
  // final String orderUrl = 'http://192.168.31.202:8080/orders';

  //내 주문목록 불러오기 => 백엔드 수정 필요
  Future<List<Order>> fetchOrders(int userId) async {
    try {
      final response = await dio.get('${orderUrl}/list?userId=$userId');
      if (response.statusCode == 200) {
        // 'content' 필드가 null인지 확인
        if (response.data['content'] != null) {
          List<dynamic> ordersJson = response.data['content'];
          List<Order> orders =
              ordersJson.map((json) => Order.fromJson(json)).toList();
          return orders;
        } else {
          // 'content'가 null이면 빈 리스트 반환
          return [];
        }
      } else {
        throw Exception('Failed to load orders: ${response.statusCode}');
      }
    } on DioError catch (e) {
      throw Exception('Error occurred while fetching orders: ${e.message}');
    }
  }

  Future<void> createOrder({
    required int userId,
    required List<Map<String, dynamic>> products,
  }) async {
    try {
      final response = await dio.post(orderUrl, data: {
        'userId': userId,
        'productTypes': products,
      });
      if (response.statusCode == 200) {
        print("주문 제대로 완료:${response.data}");
      } else {
        print("Failed to create order: ${response.statusCode}");
      }
    } on DioError catch (e) {
      print("Error occurred while creating order: ${e.message}");
    }
  }
}
