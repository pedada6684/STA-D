import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter_client_sse/constants/sse_request_type_enum.dart';
import 'package:flutter_client_sse/flutter_client_sse.dart';

class AlertServiceTemp {
  final String url = 'https://www.mystad.com/alert/connect';
  final int _maxRetries = 3;  // 최대 재시도 횟수 설정
  int _retryCount = 0;  // 현재 재시도 횟수

  void connectToSSE(String userId) {
    String fullUrl = '$url/app/$userId';
    _connect(fullUrl);
  }

  void _connect(String fullUrl) {
    SSEClient.subscribeToSSE(
        method: SSERequestType.GET,
        url: fullUrl,
        header: {
          "Accept": "text/event-stream",
          "Cache-Control": "no-cache",
          "Connection": "keep-alive"
        }).listen(
          (event) {
        debugPrint("GET Request SSE Client: ${event}");
        _retryCount = 0;  // 이벤트 수신 시 재시도 횟수 초기화
      },
      onError: (error) {
        debugPrint("Error connecting to SSE: $error");
        if (_retryCount < _maxRetries) {
          _retryCount++;
          debugPrint("Attempting reconnect ($_retryCount/$_maxRetries)");
          Future.delayed(Duration(seconds: 60), () => _connect(fullUrl));  // 재시도 로직
        } else {
          debugPrint("Max retries reached. Giving up on connection.");
        }
      },
      onDone: () {
        debugPrint("Stream closed");
      },
      cancelOnError: false,  // 오류 발생 시 자동으로 스트림을 취소하지 않음
    );
  }
}
