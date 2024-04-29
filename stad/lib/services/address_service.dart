import 'package:dio/dio.dart';
import 'package:stad/constant/api.dart';

class AddressService {
  final Dio dio = Dio();
  final String addUrl = '$locApi/user/location';

//배송지 추가
  Future<void> sendAddressData(int userId, String location, String name,
      String phone, String locationNick) async {
    try {
      final response = await dio.post(addUrl, data: {
        'userId': userId,
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
}
