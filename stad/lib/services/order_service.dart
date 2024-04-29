import 'package:dio/dio.dart';
import 'package:stad/models/order_model.dart';

class OrderService {
  final Dio dio = Dio();
  final String locUrl = 'https://www.mystad.com/api/user/location';

  // final String EmUrl = 'http://192.168.31.202:8080/api/user/location';
  final String orderUrl = 'https://www.mystad.com/api/orders/list?userId=';

  //배송지 추가
  Future<void> sendAddressData(int userId, String location, String name,
      String phone, String locationNick) async {
    try {
      final response = await dio.post(locUrl, data: {
        'userId': '1',
        'location': location,
        'name': name,
        'phone': phone,
        'locationNick': locationNick,
      });
      // 응답 처리
      if (response.statusCode == 200) {
        print('주소가 성공적으로 전송되었습니다.');
      } else {
        print('주소 전송에 실패했습니다: ${response.statusCode}');
      }
    } catch (e) {
      print('주소 전송 중 에러 발생: $e');
    }
  }

  //내 주문목록 불러오기 => 백엔드 수정 필요
  Future<List<Order>> fetchOrders(int userId) async {
    print(userId);
    print(userId);
    print(userId);
    print(userId);
    print(userId);
    try {
      final response = await dio
          // .get('https://www.mystad.com/api/orders/list?userId=${userId}');
          .get('http://10.0.2.2:8080/api/orders/list?userId=${userId}');
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
