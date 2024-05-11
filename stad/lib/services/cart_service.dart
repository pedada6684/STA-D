import 'dart:convert';

import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:stad/models/cart_model.dart';
import 'package:stad/providers/cart_provider.dart';

class CartService {
  final Dio dio = Dio();

  // final url = 'http://192.168.0.9:8080/api/cart';
  // final url = 'http://192.168.31.202:8080/api/cart';
  final url = 'https://www.mystad.com/api/cart';

  //장바구니에 추가하기
  Future<void> addProductToCart(BuildContext context, int userId,
      List<CartProductDetail> products) async {
    try {
      final response = await dio.post('$url/regist',
          data: jsonEncode({
            'userId': userId,
            'cartProductList':
                products.map((product) => product.toJson()).toList(),
          }));
      if (response.statusCode == 200) {
        print('장바구니에 잘 담겼음: ${response.data}');
        await Provider.of<CartProvider>(context, listen: false)
            .fetchCartItems(userId);
      } else {
        print("장바구니에 담기 실패: ${response.statusCode}");
      }
    } catch (e) {
      print("장바구니에 담다가 에러: $e");
    }
  }

  //장바구니 목록 조회하기
  Future<List<CartItem>> fetchCartProducts(int userId) async {
    print(userId);

    try {
      final response = await dio.get('$url/list?userId=$userId');
      print('카트 response:${response.data}');
      if (response.statusCode == 200) {
        List<dynamic> cartItemsJson = response.data['cartProductList'];
        print('카트 상품 response : $cartItemsJson');
        return cartItemsJson.map((json) => CartItem.fromJson(json)).toList();
      } else {
        print('카트 상품 받아오기 실패:${response.statusCode}');
        return [];
      }
    } catch (e) {
      print('장바구니 상품 받아오다가 에러:$e');
      return [];
    }
  }

  Future<void> deleteCartProducts(int cartProductId) async {
    print(cartProductId);

    try {
      final response = await dio
          .delete('$url/delete', data: {'cartProductId': cartProductId});

      if (response.statusCode == 200) {
        print('카트 상품 정상적으로 삭제:${response.data}');
      } else {
        print('카트 상품 삭제 실패:${response.statusCode}');
      }
    } catch (e) {
      print('카트 상품 삭제하려다가 말았습니다:$e');
    }
  }
}
