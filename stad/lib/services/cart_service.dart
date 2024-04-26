import 'dart:convert';
import 'package:http/http.dart' as http;

class CartService {
  final EmUrl = Uri.parse('http://192.168.0.9:8080/api/cart/list');

  //장바구니 목록 받아오기
  // Future<List<dynamic>> fetchCartItems(String cartId) async {
  //   var response = await http.get(Uri.parse('http://10.0.2.2:8080/api/cart/list/$cartId'));
  //
  //   if (response.statusCode == 200) {
  //     var decodedData = json.decode(response.body);
  //
  //     if (decodedData is Map<String, dynamic> && decodedData.containsKey('items')) {
  //       return List<dynamic>.from(decodedData['items']);
  //     } else {
  //       throw Exception('Invalid data format: Expected a list of items');
  //     }
  //   } else {
  //     throw Exception('Failed to load cart');
  //   }
  // }

  Future<List<dynamic>> fetchCartItems(String cartId) async {
    // 실제 서버 요청 대신 더미 데이터 반환
    return Future.delayed(
        const Duration(milliseconds: 500), () => _dummyCartData());
  }

  // CartService 클래스 안에 추가
  List<dynamic> _dummyCartData() {
    return [
      {
        "product": {
          "id": 1,
          'name':"민형이가 좋아하는 딸기",
          "thumbnail": "assets/image/product.png",
          "sellStart": "2024-01-01",
          "sellEnd": "2024-12-31",
          "cityDeliveryFee": 5000,
          "mtDeliveryFee": 5000,
          "expStart": "2024-01-01",
          "expEnd": "2024-12-31",
          "price": 10000, // price 필드가 추가되었습니다.
        },
        "quantity": 2,
        "adverseId": 123,
        "contentId": 456
      },
      {
        "product": {
          "id": 2,
          'name':"준호가 좋아하는 애플망고",
          "thumbnail": "assets/image/product2.png",
          "sellStart": "2024-01-01",
          "sellEnd": "2024-12-31",
          "cityDeliveryFee": 5000,
          "mtDeliveryFee": 5000,
          "expStart": "2024-01-01",
          "expEnd": "2024-12-31",
          "price": 10000, // price 필드가 추가되었습니다.
        },
        "quantity": 2,
        "adverseId": 123,
        "contentId": 456
      },
      // 추가적인 더미 데이터 아이템...
    ];
  }
}