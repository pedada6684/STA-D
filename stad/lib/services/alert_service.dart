import 'dart:async';
import 'dart:convert';
import 'package:dio/dio.dart';
import 'package:eventflux/eventflux.dart';
import 'package:eventflux/models/reconnect.dart';
import 'package:stad/providers/contents_provider.dart';
import 'package:stad/services/contents_service.dart';

class AlertService {
  StreamSubscription? _streamSubscription;
  final Dio dio = Dio();
  final String url = 'https://www.mystad.com/alert/connect';

  final StreamController<Map<String, dynamic>> _sseController =
      StreamController.broadcast();

  Stream<Map<String, dynamic>> get sseStream => _sseController.stream;

  void connectToSSE(String userId, ContentProvider contentProvider) {
    String fullUrl = '$url/app/1';
    // String fullUrl = '$url/app/$userId';

    EventFlux.instance.connect(
      EventFluxConnectionType.get,
      fullUrl,
      onSuccessCallback: (EventFluxResponse? response) {
        if (response != null && response.status == EventFluxStatus.connected) {
          _streamSubscription?.cancel();
          _streamSubscription = response.stream?.listen(
            (event) async {
              if (_isJson(event.data)) {
                var eventData = jsonDecode(event.data);
                print('eventData : ${eventData}');
                if (event.event == 'Content Start' &&
                    eventData is Map<String, dynamic>) {
                  int contentId = _parseContentId(eventData['contentId']);
                  if (contentId != -1) {
                    contentProvider.fetchFeaturedContent(contentId);
                  }
                } else if (event.event == 'Content Stop') {
                  contentProvider.fetchPopularContent();
                }
              } else {
                contentProvider.fetchPopularContent();
                print('Non-JSON event data: ${event.data}');
              }
            },
            onError: (error) {
              print('Error in stream: $error');
              _reconnect(userId, contentProvider);
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
        _reconnect(userId, contentProvider);
      },
      onConnectionClose: () {
        print('Connection Closed');
        _reconnect(userId, contentProvider);
      },
    );
  }

  bool _isJson(String str) {
    try {
      jsonDecode(str);
      return true;
    } catch (e) {
      return false;
    }
  }

  int _parseContentId(dynamic contentId) {
    if (contentId is int) {
      return contentId;
    } else if (contentId is String) {
      return int.tryParse(contentId) ?? -1;
    } else {
      return -1;
    }
  }

  Future<List<Map<String, dynamic>>> fetchPopularContent() async {
    ContentsService contentsService = ContentsService();
    return await contentsService.fetchPopularContent();
  }

  void _reconnect(String userId, ContentProvider contentProvider) {
    if (_streamSubscription != null) {
      _streamSubscription!.cancel();
      _streamSubscription = null;
    }
    connectToSSE(userId, contentProvider);
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
