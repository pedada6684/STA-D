import 'package:dio/dio.dart';
import 'package:stad/models/product_model.dart';

class ProductService {
  final Dio _dio = Dio();

  final String _baseUrl = 'https://www.mystad.com/api';

  ProductService() {
    _dio.options.baseUrl = _baseUrl;
  }

  //상품 정보 조회
  //TODO: advertId 수정하기
  // Future<ProductInfo> getProductInfo(int advertId) async {
  //   try {
  //     final response = await _dio.get('/product/list', queryParameters: {'advertId': 3});
  //     print('광고 상품 정보 조회 response :${response.data}');
  //     if (response.statusCode == 200) {
  //       var productList = response.data['productList'];
  //       if (productList != null && productList.isNotEmpty) {
  //         return ProductInfo.fromJson(productList[0]);  // 첫 번째 상품 정보만 사용
  //       } else {
  //         throw Exception('Product list is empty');
  //       }
  //     } else {
  //       throw Exception('Failed to load product info with status code ${response.statusCode}');
  //     }
  //   } catch (e) {
  //     print('Error fetching product info: $e');
  //     throw Exception('Error fetching product info: $e');
  //   }
  // }
  Future<ProductInfo> getProductInfo(int advertId) async {
    try {
      final response = await _dio
          .get('/product/list', queryParameters: {'advertId': advertId});
      print('광고 상품 정보 조회 response : ${response.data}');
      if (response.statusCode == 200) {
        var responseData = response.data;
        var productList = responseData['productList'] as List; // List로 명시적 형 변환

        print(productList);


        if (productList.isNotEmpty) {
          var firstProduct = productList[0]
              as Map<String, dynamic>; // Map<String, dynamic>로 명시적 형 변환
          print(firstProduct);
  

          return ProductInfo.fromJson(firstProduct);
        } else {
          throw Exception('Product list is empty');
        }
      } else {
        throw Exception(
            'Failed to load product info with status code ${response.statusCode}');
      }
    } catch (e) {
      print('Error fetching product info: $e');
      throw Exception('Error fetching product info: $e');
    }
  }

  // 특정 광고에 속한 상품의 타입 리스트 조회
  //TODO: productId 수정하기
  Future<List<ProductType>> getProductTypeList(int productId) async {
    try {
      final response =
          await _dio.get('/type/list', queryParameters: {'productId': productId});
      print('광고 상품 타입 리스트 response :${response.data}');
      if (response.statusCode == 200) {
        List<dynamic> typeList = response.data['list'];
        return typeList.map((data) => ProductType.fromJson(data)).toList();
      } else {
        throw Exception('Failed to load product types');
      }
    } catch (e) {
      print('Error fetching product types: $e');
      throw Exception('Error fetching product types: $e');
    }
  }
}
