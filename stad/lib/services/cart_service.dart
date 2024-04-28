import 'package:stad/models/cart_model.dart';

class CartService {
  // 더미 데이터를 반환하는 메서드
  List<CartItem> getDummyCartData() {
    List<dynamic> dummyData = _dummyCartData();
    print('더미덤디ㅓ미더미더미더미ㅓ디');
    print(dummyData);
    return dummyData.map((data) {
      return CartItem.fromJson(data);
    }).toList();
    
  }

  // 서버에서 장바구니 목록을 조회하는 메서드
  Future<List<CartItem>> fetchCartItems() async {
    print('fetchCartItems');
    print('fetchCartItems');
    print('fetchCartItems');
    print('fetchCartItems');
    return getDummyCartData();

    //TODO: 서버에서 데이터 불러오는 코드 필요
  }

  // 특정 장바구니 항목을 삭제하는 메서드
  Future<void> deleteCartItem(String id) async {
    // TODO: http.delete(Uri.parse('https://example.com/api/cart/delete/$id'));

    print('Delete item with id: $id');
    // 실제로는 서버에 DELETE 요청을 보내야 합니다.
  }

  // 장바구니에 항목을 추가하는 메서드
  Future<void> addCartItem(CartItem item) async {
    // TODO: http.post(Uri.parse('https://example.com/api/cart/add'), body: item.toJson());
  }

  // TODO: 장바구니 항목 수량을 업데이트
  Future<void> updateCartItemQuantity(String id, int quantity) async {}

  // 더미 데이터
  List<dynamic> _dummyCartData() {
    return [
      {
        "product": {
          "id": 1,
          "name": "민형이가 좋아하는 딸기",
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
          "name": "준호가 좋아하는 애플망고",
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
    ];
  }
}