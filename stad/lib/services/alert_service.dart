import 'package:dio/dio.dart';
import 'package:sse_channel/sse_channel.dart';

class AlertService {
  final Dio dio = Dio();

  // final String url = 'http://192.168.31.202:8081/alert/connect';
  // final String url = 'http://192.168.0.9:8081/alert/connect';
  final String url = 'https://www.mystad.com/alert/connect';
  SseChannel? _sseChannel;

//sse
  void connectToSSE(String userId) {
    print('connectToSSE:$userId');

    _sseChannel = SseChannel.connect(
        Uri.parse('https://www.mystad.com/alert/connect/app/$userId'));
    print(_sseChannel.toString());
    print(_sseChannel.toString());
    print(_sseChannel.toString());
    print(_sseChannel.toString());

    _sseChannel!.stream.listen((event) {
      print('히히히히');
      print('SSE event:$event');
    });
  }

  void dispose() {
    _sseChannel?.sink.close();
  }

  Future<bool> sendQrResponse(int userId, String tvId) async {
    print(tvId.toString());

    try {
      final response = await dio.post('$url/qrlogin', data: {
        'userId': userId,
        'tvId': tvId,
      });
      if (response.statusCode == 200) {
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
