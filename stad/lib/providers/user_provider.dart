//사용자 인증상태, 토큰, 쿠키 관리하기

import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/material.dart';
import 'package:jwt_decode/jwt_decode.dart';
import 'package:stad/models/user_model.dart';
import 'package:stad/services/user_secure_storage.dart';

class UserProvider with ChangeNotifier {
  UserModel? _user;
  String? _token;
  String? _cookie;
  int? _userId;
  bool _isLoggedIn = false;

  UserModel? get user => _user;

  String? get token => _token;

  String? get cookie => _cookie;

  int? get userId => _userId;

  bool get isLoggedIn => _isLoggedIn;

  void setUser(UserModel user) async {
    _user = user;
    _isLoggedIn = true;
    notifyListeners();
  }

  void setToken(String token) {
    _token = token;
    _setUserIdFromToken(token); // 이 부분에서 토큰으로부터 userId를 추출하여 설정합니다.
    notifyListeners();
  }

  void _setUserIdFromToken(String token) async {
    try {
      Map<String, dynamic> payload = Jwt.parseJwt(token);
      _userId = int.tryParse(payload['sub']);
      print('토큰에서 설정된 사용자 ID: $_userId');
      await AuthService()
          .saveToken(token, _userId.toString()); // 토큰과 함께 userId 저장
      notifyListeners();
    } catch (e) {
      print('사용자 ID를 파싱하는 중 오류 발생: $e');
    }
  }

  void setUserId(int userId) {
    _userId = userId;
    notifyListeners();
  }

  void setCookie(String cookie) {
    _cookie = cookie;
    notifyListeners();
  }

  void clearUser() {
    _user = null;
    _token = null;
    _cookie = null;
    _isLoggedIn = false;
    _userId = null;

    notifyListeners();
  }

  Future<void> fetchUser() async {
    User? firebaseUser = FirebaseAuth.instance.currentUser;
    if (firebaseUser != null) {
      String? accessToken;
      UserModel userModel =
          UserModel.fromFirebaseUser(firebaseUser, accessToken);
      setUser(userModel);

      String? userId = await AuthService().getUserId();
      if (userId != null) {
        setUserId(int.parse(userId));
      }
    }
  }
}
