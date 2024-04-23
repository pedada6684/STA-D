import 'package:dio/dio.dart';

class OrderService {
  final Dio dio = Dio();
  final String url = 'http://10.0.2.2:8080/api/user/location';

  Future<void> sendAddressData(int userId, String location, String name,
      String phone, String locationNick) async {
    try {
      final response = await dio.post(url, data: {
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
}
