import 'package:dio/dio.dart';

class AlertService {
  final Dio dio = Dio();
  // final String url = 'http://192.168.31.202:8081/alert/connect';
  // final String url = 'http://192.168.0.9:8081/alert/connect';
  final String url = 'https://www.mystad.com/alert/connect';

  Future<bool> sendQrResponse(int userId, String tvId) async {
    print(tvId.toString());

    try {
      final response = await dio.post('$url/qrlogin', data: {
        'userId': userId,
        'tvId': tvId,
      });
      if (response.statusCode == 200) {
        print('qr로그인 성공 : ${response.data}');
        print('qr로그인 성공 : ${response.data}');
        print('qr로그인 성공 : ${response.data}');
        return true;
      } else {
        print('qr로그인 안됐음 : ${response.statusCode}');
        print('qr no : ${response.data}');
        return false;
      }
    } on DioError catch (e) {
      print('qr로그인 요청 보내다가 실패 : $e');
      return false;
    }
  }
}
