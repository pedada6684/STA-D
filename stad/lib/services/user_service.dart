import 'dart:convert';

import 'package:dio/dio.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import 'package:google_sign_in/google_sign_in.dart';
import 'package:jwt_decode/jwt_decode.dart';
import 'package:provider/provider.dart';
import 'package:stad/models/user_model.dart';
import 'package:stad/providers/user_provider.dart';
import 'package:stad/services/alert_service.dart';
import 'package:stad/services/user_secure_storage.dart';

class UserService {
  final FirebaseAuth _firebaseAuth = FirebaseAuth.instance;
  final GoogleSignIn _googleSignIn = GoogleSignIn(
    scopes: ['email', 'https://www.googleapis.com/auth/youtube.readonly'],
  );
  final Dio dio = Dio();
  final AlertService alertService = AlertService();
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
        'https://www.mystad.com/api/v1/auth/applogin',
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

  Future<bool> updateUserProfile(BuildContext context, String? nickname,
      String? phone, String? profileImagePath) async {
    final userProvider = Provider.of<UserProvider>(context, listen: false);
    UserModel currentUser = userProvider.user!;

    String finalNickname =
        nickname?.isNotEmpty == true ? nickname! : currentUser.nickname ?? '';
    String finalPhone =
        phone?.isNotEmpty == true ? phone! : currentUser.phone ?? '';

    Map<String, dynamic> updateData = {
      "userId": userProvider.userId,
      "name":currentUser.name,
      "nickname": finalNickname,
      "phone": finalPhone,
      "company":null,
      "comNo":null,
      "department":null,
      "password":null,
    };

    FormData formData = FormData.fromMap(updateData);

    if (profileImagePath != null && profileImagePath.isNotEmpty) {
      formData.files.add(
        MapEntry(
            "profile",
            await MultipartFile.fromFile(profileImagePath, filename: "profile_pic.png")
        ),
      );
    }

    print(formData);

    //TODO 수정합시다 잘 안되요ㅗㅇ
    try {
      final response = await dio.post(
        'https://www.mystad.com/api/user/update',
        data: formData,
        options: Options(
          headers: {
            'Authorization': 'Bearer ${userProvider.token}',
          },
        ),
      );

      if (response.statusCode == 200) {
        UserModel updatedUser = UserModel.fromJson(response.data);
        Provider.of<UserProvider>(context, listen: false).setUser(updatedUser);
        return true;
      } else {
        print('Update failed with status code: ${response.statusCode}');
        return false;
      }
    } catch (e) {
      print('Error updating user profile: $e');
      return false;
    }
  }

  //프로필 사진 업데이트
  Future<bool> updateProfileImage(
      BuildContext context, String profileImagePath) async {
    final userProvider = Provider.of<UserProvider>(context, listen: false);
    FormData formData = FormData.fromMap({
      "userId": userProvider.userId,
      "profile": await MultipartFile.fromFile(profileImagePath,
          filename: "profile_pic.png"),
    });

    try {
      final response = await dio.post(
        'https://www.mystad.com/api/user/profile',
        data: formData,
        options: Options(
          headers: {
            'Cookie': userProvider.cookie,
            'Authorization': 'Bearer ${userProvider.token}',
          },
        ),
      );

      if (response.statusCode == 200) {
        // Assuming the response contains the updated profile picture URL
        UserModel updatedUser = userProvider.user!
            .copyWith(profilePicture: response.data['profileImgUrl']);
        userProvider.setUser(updatedUser);
        return true;
      }
    } on DioException catch (e) {
      print('Error updating profile image: ${e.response}');
      return false;
    }
    return false;
  }

  Future<void> signOut(BuildContext context) async {
    final userProvider = Provider.of<UserProvider>(context, listen: false);
    final userId = userProvider.userId;

    try {
      await _googleSignIn.signOut();
      await _firebaseAuth.signOut();

      // 서버 로그아웃 요청 보내기
      await dio.post(
        'https://www.mystad.com/api/user/logout?userId=$userId',
        options: Options(
          headers: {
            'Cookie': userProvider.cookie,
            'Authorization': 'Bearer ${userProvider.token}',
          },
        ),
      );

      // SSE 연결 해제
      alertService.disconnect();

      // 사용자 정보 삭제
      userProvider.clearUser();

      // Secure Storage 정보 삭제
      await AuthService().deleteToken();

      // 로그인 페이지로 이동
      GoRouter.of(context).go('/login');
    } catch (error) {
      print('로그아웃 실패: $error');
    }
  }
}
