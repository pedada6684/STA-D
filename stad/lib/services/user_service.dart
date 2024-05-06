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
        'http://192.168.0.9:8080/api/v1/auth/applogin',
        // 'http://192.168.31.202:8080/api/v1/auth/applogin',
        // 'http://192.168.0.129:8080/api/v1/auth/applogin',
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

    // 사용자가 입력하지 않은 경우 기존 값을 사용합니다.
    String finalNickname =
        nickname?.isNotEmpty == true ? nickname! : currentUser.nickname ?? '';
    String finalPhone =
        phone?.isNotEmpty == true ? phone! : currentUser.phone ?? '';
    print(finalNickname);
    print(finalPhone);

    Map<String, dynamic> updateData = {
      "userId": userProvider.userId,
      "name": currentUser.name, // name은 변경하지 않으므로 기존 값을 그대로 사용합니다.
      "nickname": finalNickname,
      "phone": finalPhone,
    };

    // 파일이 선택된 경우에만 파일을 FormData에 추가
    if (profileImagePath != null && profileImagePath.isNotEmpty) {
      updateData['profile'] = await MultipartFile.fromFile(profileImagePath,
          filename: "profile_pic.png");
    } else if (currentUser.profilePicture != null &&
        currentUser.profilePicture!.isNotEmpty) {
      // 서버 API가 이를 처리할 수 있도록 기존 이미지 URL을 재전송하거나, 필요 없다면 이 라인을 제거
      updateData['profile'] = currentUser.profilePicture;
    }

    FormData formData = FormData.fromMap(updateData);

    //
    // FormData formData = FormData.fromMap({
    //   "userId": userProvider.userId,
    //   "name": userProvider.user?.name,
    //   "nickname": finalNickname,
    //   "phone": finalPhone,
    //   "profile": await MultipartFile.fromFile(profileImagePath,
    //       filename: "profile_pic.png"),
    // });

    print(formData);

    try {
      final response = await dio.post(
        // 'https://www.mystad.com/api/user/update',
        'http://192.168.0.9:8080/api/user/update',
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
