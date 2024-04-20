//사용자 인증상태, 토큰, 쿠키 관리하기

import 'package:flutter/material.dart';
import 'package:stad/models/user_model.dart';

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

  void setUser(UserModel user) {
    _user = user;
    _isLoggedIn = true;
    notifyListeners();
  }

  void setToken(String token) {
    _token = token;
    print('토큰토큰토큰토큰토큰 : $token');
    notifyListeners();
  }

  void setCookie(String cookie) {
    _cookie = cookie;
    print('내가 만든 쿠키쿠키ㅜ키퀴퀴퀴퀴퀴 cookie : $cookie');
    notifyListeners();
  }

  void setUserId(int UserId) {
    _userId = userId;
    print('사용자 userId : $userId');
    notifyListeners();
  }

  void clearUser() {
    _user = null;
    _token = null;
    _cookie = null;
    _isLoggedIn = false;
    notifyListeners();
  }
}
