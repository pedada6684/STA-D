//사용자 인증상태, 토큰, 쿠키 관리하기

import 'package:firebase_auth/firebase_auth.dart';
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

  Future<void> setUser(UserModel user) async {
    _user = user;
    _isLoggedIn = true;
    print('User updated: ${user.toJson()}');
    print('User updated: ${user.toJson()}');
    print('User updated: ${user.toJson()}');
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

  void setUserId(int userId) {
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

  Future<void> fetchUser() async {
    User? firebaseUser = FirebaseAuth.instance.currentUser;
    if (firebaseUser != null) {
      String? accessToken;
      UserModel userModel =
          UserModel.fromFirebaseUser(firebaseUser, accessToken);
      setUser(userModel);
    }
  }
}
