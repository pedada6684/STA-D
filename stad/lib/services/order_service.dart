import 'package:dio/dio.dart';
import 'package:stad/constant/api.dart';
import 'package:stad/models/order_model.dart';

class OrderService {
  final Dio dio = Dio();

  // final String EmUrl = 'http://192.168.31.202:8080/api/user/location';
  final String orderUrl = '$locApi/orders/list?userId=';

  //내 주문목록 불러오기 => 백엔드 수정 필요
  Future<List<Order>> fetchOrders(int userId) async {
    print('fetchOrders : $userId');
    print('fetchOrders : $userId');
    print('fetchOrders : $userId');
    print('fetchOrders : $userId');
    print('fetchOrders : $userId');
    print('fetchOrders : $userId');

    try {
      final response = await dio
          // .get('https://www.mystad.com/api/orders/list?userId=${userId}');
          .get('${orderUrl}$userId');
      if (response.statusCode == 200) {
        List<dynamic> ordersJson = response.data['content'];
        List<Order> orders =
            ordersJson.map((json) => Order.fromJson(json)).toList();
        print('주문 목록 불러와지나? : $orders');
        return orders;
      } else {
        print('주문 목록 불러오기 대실패:${response.data}');
        throw Exception('Failed to load orders');
      }
    } on DioError catch (e) {
      print('DioError caught: ${e.response?.statusCode} - ${e.response?.data}');
      throw Exception('Error occurred while fetching orders: ${e.message}');
    }
  }
}
