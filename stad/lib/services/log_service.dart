import 'package:dio/dio.dart';

class LogService {
  final Dio dio = Dio();

  final String logUrl = 'http://192.168.31.202:8080/log';

  Future<void> createOrder({
    required int advertId,
    required int advertVideoId,
    required int contentId,
    required int userId,
  }) async {
    try {
      final response = await dio.post('${logUrl}/list?userId=$userId', data: {
        'userId': userId,
        'advertId': advertId,
        'advertVideoId': advertVideoId,
        'contentId': contentId
      });
      if (response.statusCode == 200) {
        print("클릭 로그 제대로 완료:${response.data}");
      } else {
        print("Failed to create order: ${response.statusCode}");
      }
    } on DioError catch (e) {
      print("Error occurred while creating order: ${e.message}");
    }
  }
}