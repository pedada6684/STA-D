import 'dart:async';
import 'dart:convert';

import 'package:dio/dio.dart';
import 'package:eventflux/eventflux.dart';
import 'package:eventflux/models/reconnect.dart';
import 'package:stad/constant/api.dart';
import 'package:stad/services/contents_service.dart';

class AlertService {
  StreamSubscription? _streamSubscription;
  final Dio dio = Dio();

  bool _isInitialConnectionEstablished = false;
  final String url = 'https://www.mystad.com/alert/connect';

  final StreamController<Map<String, dynamic>> _sseController =
      StreamController.broadcast();

  Stream<Map<String, dynamic>> get sseStream => _sseController.stream;

  void connectToSSE(String userId) {
    String fullUrl = '$url/app/$userId';

    EventFlux.instance.connect(
      EventFluxConnectionType.get,
      fullUrl,
      onSuccessCallback: (EventFluxResponse? response) {
        if (response != null && response.status == EventFluxStatus.connected) {
          print('이거는 뭘까: $response');

          _streamSubscription?.cancel();
          _streamSubscription = response.stream?.listen(
            (event) async {
              var eventData = event.data;
              if (eventData is String) {
                print('connect? : ${eventData}');
                if (eventData.startsWith('SSE connect')) {
                  print('성공성공');
                }
              } else if (eventData is Map<String, dynamic>) {
                print('Ids : $eventData');
                Map<String, dynamic> eventDataJson =
                    eventData as Map<String, dynamic>;

                if (!eventDataJson.containsKey('contentId')) {
                  List<Map<String, dynamic>> popularContent =
                      await fetchPopularContent();
                  eventDataJson['popularContent'] = popularContent;
                  _sseController.add(eventDataJson);
                } else {
                  _sseController.add(eventDataJson);
                }
              } else {
                try {
                  Map<String, dynamic> eventDataJson = json.decode(eventData);
                  print('Json으로 파싱:$eventDataJson');

                  if (!eventDataJson.containsKey('contentId')) {
                    List<Map<String, dynamic>> popularContent =
                        await fetchPopularContent();
                    eventDataJson['popularContent'] = popularContent;
                  }
                  _sseController.add(eventDataJson);
                } catch (e) {
                  print('이벤트 데이터 json으로 파싱하다가 에러 : $e');
                }
              }
            },
            onError: (error) {
              print('Error in stream: $error');
              _reconnect(userId);
            },
            onDone: () {
              print('Stream closed');
            },
            cancelOnError: false,
          );
        }
      },
      autoReconnect: true,
      header: {
        "Accept": "text/event-stream",
        "Cache-Control": "no-cache",
        "Connection": "keep-alive",
        "Content-Type": "application/json",
      },
      reconnectConfig: ReconnectConfig(
          mode: ReconnectMode.linear,
          interval: Duration(seconds: 3),
          maxAttempts: -1,
          onReconnect: () => print('reconnecting')),
      onError: (error) {
        print('Error connecting to SSE: ${error.message}');
        _reconnect(userId);
      },
      onConnectionClose: () {
        print('Connection Closed');
        _reconnect(userId);
      },
    );
  }

  Future<List<Map<String, dynamic>>> fetchPopularContent() async {
    ContentsService contentsService = ContentsService();
    return await contentsService.fetchPopularContent();
  }

  void _reconnect(String userId) {
    if (_streamSubscription != null) {
      _streamSubscription!.cancel();
      _streamSubscription = null;
    }
    connectToSSE(userId);
  }

  void disconnect() {
    _streamSubscription?.cancel();
    _streamSubscription = null;
    EventFlux.instance.disconnect().then((status) {
      print('Disconnected: $status');
    }).catchError((error) {
      print('Error disconnecting: $error');
    });
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
