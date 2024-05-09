import 'package:dio/dio.dart';
import 'package:stad/constant/api.dart';
import 'package:stad/models/delivery_address_model.dart';

class AddressService {
  final Dio dio = Dio();

  final String apiUrl = 'http://192.168.31.202:8080/api/user/location';
  // final String apiUrl = 'http://192.168.0.9:8080/api/user/location';
  // final String apiUrl = '$svApi/user/location';

  // 배송지 목록 조회
  Future<List<DeliveryAddress>> fetchAddresses(int userId) async {
    try {
      final response =
          await dio.get('$apiUrl', queryParameters: {'userId': userId});
      if (response.statusCode == 200) {
        print(response.data);
        print(response.data);
        print(response.data);
        List<dynamic> addressList = response.data['userLocationList'] as List;
        return addressList
            .map((e) => DeliveryAddress.fromJson(e as Map<String, dynamic>))
            .toList();
      } else {
        throw Exception('Failed to fetch addresses');
        print(userId);
      }
    } catch (e) {
      print('Error fetching addresses: $e');
      throw Exception('Error occurred while fetching addresses: $e');
    }
  }

  // 배송지 추가
  Future<void> addAddress(Map<String, dynamic> addressData) async {
    try {
      final response = await dio.post(apiUrl, data: addressData);
      if (response.statusCode == 200) {
        print('Address successfully added');
        // print(response.data);
        // print(response.data);
        // print(response.data);
        // print(response.data);
      } else {
        print('Failed to add address: ${response.statusCode}');
        throw Exception('Failed to add address');
      }
    } catch (e) {
      print('Error adding address: $e');
      throw Exception('Error occurred while adding address: $e');
    }
  }

  // // 배송지 수정
  // Future<void> updateAddress(DeliveryAddress address) async {
  //   try {
  //     final response = await dio.put('$apiUrl/${address.userId}', data: address.toJson());
  //     if (response.statusCode == 200) {
  //       print('Address successfully updated');
  //     } else {
  //       print('Failed to update address: ${response.statusCode}');
  //       throw Exception('Failed to update address');
  //     }
  //   } catch (e) {
  //     print('Error updating address: $e');
  //     throw Exception('Error occurred while updating address: $e');
  //   }
  // }

  // 배송지 삭제
  Future<void> deleteAddress(int userId, int locationId) async {
    try {
      // Adjust the URL if needed or verify that it matches your server setup
      final response = await dio.delete('$apiUrl',
          queryParameters: {'userId': userId, 'locationId': locationId});
      if (response.statusCode == 200) {
        print('Address successfully deleted');
      } else {
        print('Failed to delete address: ${response.statusCode}');
        throw Exception('Failed to delete address');
      }
    } catch (e) {
      print('Error deleting address: $e');
      throw Exception('Error occurred while deleting address: $e');
    }
  }
}
