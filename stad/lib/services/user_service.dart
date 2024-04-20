import 'dart:convert';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:google_sign_in/google_sign_in.dart';
import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:jwt_decode/jwt_decode.dart';
import 'package:provider/provider.dart';
import 'package:stad/models/user_model.dart';
import 'package:stad/providers/user_provider.dart';

class UserService {
  final FirebaseAuth _firebaseAuth = FirebaseAuth.instance;
  final GoogleSignIn _googleSignIn = GoogleSignIn();
  final Dio dio = Dio();

  Future<User?> signInWithGoogle(BuildContext context) async {
    final GoogleSignInAccount? googleUser = await _googleSignIn.signIn();
    if (googleUser == null) return null;

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
      await sendUserProfile(context, user, googleAuth.accessToken);
      print('User signed in : ${user}');
      return user;
    }
    return null;
  }

  Future<void> sendUserProfile(
      BuildContext context, User user, String? googleAccessToken) async {
    final userProfile =
        UserModel.fromFirebaseUser(user, googleAccessToken).toJson();

    try {
      final response = await dio.post(
        'http://10.0.2.2:8080/api/v1/auth/applogin',
        data: json.encode(userProfile),
        options: Options(
            followRedirects: false, validateStatus: (status) => status! < 500),
      );

      if (response.statusCode == 200) {
        //토큰
        String responseHeader = response.headers['Authorization']![0];
        String token = responseHeader.replaceFirst('Bearer ', '');
        Provider.of<UserProvider>(context, listen: false).setToken(token);

        Map<String, dynamic> payload = Jwt.parseJwt(token);
        String userIdStr = payload['sub'] as String;
        int userId = int.tryParse(userIdStr) ?? 0;

        print('payload : $payload');

        Provider.of<UserProvider>(context, listen: false).setUserId(userId);

        //쿠키
        Provider.of<UserProvider>(context, listen: false)
            .setCookie(response.headers['Set-Cookie']![0]);
      } else {
        throw Exception(
            'Failed to send user profile: Status code ${response.statusCode}, Body: ${response}');
      }
    } catch (e) {
      print('Error sending user profile: $e');
    }
  }

  Future<void> signOut() async {
    await _googleSignIn.signOut();
    await _firebaseAuth.signOut();
  }
}
