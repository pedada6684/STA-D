import 'dart:convert';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:go_router/go_router.dart';
import 'package:google_sign_in/google_sign_in.dart';
import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:jwt_decode/jwt_decode.dart';
import 'package:provider/provider.dart';
import 'package:stad/main.dart';
import 'package:stad/models/user_model.dart';
import 'package:stad/providers/user_provider.dart';
import 'package:stad/services/user_secure_storage.dart';

class UserService {
  final FirebaseAuth _firebaseAuth = FirebaseAuth.instance;
  final GoogleSignIn _googleSignIn = GoogleSignIn();
  final Dio dio = Dio();
  final EmUrl = Uri.parse('http://192.168.31.202:8080/api/v1/auth/applogin');
  final locUrl = Uri.parse('http://10.0.2.2:8080/api/v1/auth/applogin');

  Future<void> signInWithGoogle(BuildContext context) async {
    final GoogleSignInAccount? googleUser = await _googleSignIn.signIn();
    if (googleUser == null) return;

    final GoogleSignInAuthentication googleAuth =
        await googleUser.authentication;
    final OAuthCredential credential = GoogleAuthProvider.credential(
      accessToken: googleAuth.accessToken,
      idToken: googleAuth.idToken,
    );

    final UserCredential userCredential =
        await _firebaseAuth.signInWithCredential(credential);
    final User? user = userCredential.user;

    if (user != null) {
      UserModel userModel =
          UserModel.fromFirebaseUser(user, googleAuth.accessToken);

      print(userModel.nickname);
      print(userModel.googleAccessToken);

      // 서버로 사용자 프로필을 전송하고 응답을 기다림
      bool profileSent =
          await sendUserProfile(context, user, googleAuth.accessToken);
      if (profileSent) {
        Provider.of<UserProvider>(context, listen: false).setUser(userModel);
        context.go('/home');
      } else {
        // 에러 처리 로직
        print('Failed to send user profile');
      }
    }
  }

  Future<bool> sendUserProfile(
      BuildContext context, User user, String? googleAccessToken) async {
    final userProfile =
        UserModel.fromFirebaseUser(user, googleAccessToken).toJson();
    try {
      final response = await dio.post(
        // 'https://www.mystad.com/api/v1/auth/applogin',
        // 'http://10.0.2.2:8080/api/v1/auth/applogin',
        // 'http://192.168.31.202:8080/api/v1/auth/applogin',
        'http://192.168.0.129:8080/api/v1/auth/applogin',
        data: json.encode(userProfile),
        options: Options(
            followRedirects: false, validateStatus: (status) => status! < 500),
      );

      if (response.statusCode == 200) {
        String token =
            response.headers['Authorization']![0].replaceFirst('Bearer ', '');
        Provider.of<UserProvider>(context, listen: false).setToken(token);

        Map<String, dynamic> payload = Jwt.parseJwt(token);
        int userId = int.tryParse(payload['sub'] as String) ?? 0;
        await AuthService().saveUserId(userId.toString());

        Provider.of<UserProvider>(context, listen: false).setUserId(userId);
        Provider.of<UserProvider>(context, listen: false)
            .setCookie(response.headers['Set-Cookie']![0]);

        return true;
      }
    } catch (e) {
      print('Error sending user profile: $e');
    }
    return false;
  }

  Future<bool> updateUserProfile(
      BuildContext context, String phone, String profileImagePath) async {
    final userProvider = Provider.of<UserProvider>(context, listen: false);
    FormData formData = FormData.fromMap({
      "userId": userProvider.userId,
      // "userId": 2,
      "name": userProvider.user?.name,
      "nickname": userProvider.user?.nickname,
      "phone": phone,
      // "company": null,
      // "comNo": null,
      // "department": null,
      // "password": null,
      "profile": await MultipartFile.fromFile(profileImagePath,
          filename: "profile_pic.png"),
    });

    print(formData);

    try {
      final response = await dio.post(
        // 'https://www.mystad.com/api/user/update',
        'http://http://192.168.0.129:8080/api/user/update',
        data: formData,
        options: Options(
          headers: {
            'Cookie': userProvider.cookie,
            'Authorization': 'Bearer ${userProvider.token}',
          },
        ),
      );

      if (response.statusCode == 200) {
        // 서버로부터 받은 응답으로 UserModel 업데이트
        UserModel updatedUser = UserModel.fromJson(response.data);
        userProvider.setUser(updatedUser); // UserProvider 업데이트
        return true;
      }
    } catch (e) {
      print('Error updating user profile: $e');
      return false;
    }
    return false;
  }

  Future<void> signOut(BuildContext context) async {
    await _googleSignIn.signOut();
    await _firebaseAuth.signOut();

    Provider.of<UserProvider>(context, listen: false).clearUser();
  }
}
