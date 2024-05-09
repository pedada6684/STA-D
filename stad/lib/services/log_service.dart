import 'package:dio/dio.dart';

class LogService {
  final Dio dio = Dio();
  final String logUrl = 'http://192.168.31.202:8080/api/log';
  // final String logUrl = 'https://www.mystad.com/api/log';

  Future<void> advertClickLog({
    required int advertId,
    required int advertVideoId,
    required int contentId,
    required int userId,
  }) async {
    try {
      print('asdfasdfasfasdfasdfasdfasfasdfasdsadfasdfasdfasdf:$logUrl/click');
      final response = await dio.post('$logUrl/click', data: {
        'userId': userId,
        'advertId': advertId,
        'advertVideoId': advertVideoId,
        'contentId': contentId
      });
      print("response:$response");
      if (response.statusCode == 200) {
        print("클릭 로그 제대로 완료:${response.data}");
      } else {
        print("Failed to clickLogr: ${response.statusCode}");
      }
    } on DioError catch (e) {
      print("Error occurred while clickLog: ${e.message}");
    }
  }
}