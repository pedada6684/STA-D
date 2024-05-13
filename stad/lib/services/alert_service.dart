import 'package:dio/dio.dart';
import 'package:sse_channel/sse_channel.dart';

class AlertService {
  final Dio dio = Dio();

  // final String url = 'http://192.168.31.202:8081/alert/connect';
  // final String url = 'http://192.168.0.9:8081/alert/connect';
  final String url = 'https://www.mystad.com/alert/connect';
  SseChannel? _sseChannel;
  bool _isInitialConnectionEstablished = false;

//sse
  void connectToSSE(String userId) {
    print('connectToSSE:$userId');
    String fullUrl = '$url/app/$userId';
    _sseChannel = SseChannel.connect(Uri.parse(fullUrl));

    print(_sseChannel.toString());
    print(_sseChannel.toString());
    print(_sseChannel.toString());
    print(_sseChannel.toString());

    _sseChannel!.stream.listen((event) {
      // "SSE connected" 메시지 수신 확인
      if (!_isInitialConnectionEstablished && event == "SSE connected") {
        _isInitialConnectionEstablished = true;
        print("Connection established. Ready to receive data.");
        // 이후 로직에서 데이터 수신 및 처리 시작
      } else if (_isInitialConnectionEstablished) {
        // 실제 이벤트 처리
        print("Received event: $event");
      }
    }, onDone: () {
      print('SSE stream closed.');
      _isInitialConnectionEstablished = false;
      //종료되면 다시 연결
      connectToSSE(userId);
    }, onError: (error) {
      print('Error in SSE stream: $error');
      _isInitialConnectionEstablished = false;
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
