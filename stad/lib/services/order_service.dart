import 'dart:convert';

import 'package:dio/dio.dart';
import 'package:stad/constant/api.dart';
import 'package:stad/models/order_model.dart';

class OrderService {
  final Dio dio = Dio();

  // final String orderUrl = 'http://192.168.0.9:8080/api/user/location';
  // final String orderUrl = '$svApi/orders/list?userId=';
  // final String orderUrl = 'http://192.168.0.9:8080/api/orders';
  // final String orderUrl = 'http://192.168.31.202:8080/orders';
  final String orderUrl = '$svApi/orders';
  
  //내 주문목록 불러오기 => 백엔드 수정 필요
  Future<List<Order>> fetchOrders(int userId) async {
    try {
      final response = await dio.get('${orderUrl}/list?userId=$userId');
      if (response.statusCode == 200) {
        // 'content' 필드가 null인지 확인
        print('내 주문 정보 : ${response.data}');
        if (response.data['content'] != null) {
          List<dynamic> ordersJson = response.data['data'];
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

  //주문 생성하기

  Future<void> createOrder({
    required int userId,
    required List<Map<String, dynamic>> products,
  }) async {
    try {
      final response = await dio.post(orderUrl,
          data: jsonEncode([
            {
              'userId': userId,
              'productTypes': products
                  .map((product) => {
                        'productTypeId': product['productTypeId'],
                        'productCnt': product['productCnt'],
                        'optionId': product['optionId'],
                        'contentId': product['contentId'],
                        'advertId': product['advertId'],
                      })
                  .toList(),
            }
          ]),
          options: Options(headers: {'Content-Type': 'application/json'}));
      if (response.statusCode == 200) {
        print("주문 제대로 완료:${response.data}");
      } else {
        print("Failed to create order: ${response.statusCode}");
        print('제발 주문 좀 되게 해주세요:${response.data}');
      }
    } on DioError catch (e) {
      print("Error occurred while creating order: ${e.message}");
      if (e.response != null) {
        print('주문에러 response data:${e.response!.data}');
      }
    }
  }
}
