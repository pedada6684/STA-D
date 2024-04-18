import 'dart:convert';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:google_sign_in/google_sign_in.dart';
import 'package:http/http.dart' as http;
import 'package:stad/models/user_model.dart';

class UserService {
  final String _baseUrl = 'http://10.0.2.2:8080/api/v1/auth/applogin';
  final GoogleSignIn _googleSignIn = GoogleSignIn();
  final FirebaseAuth _firebaseAuth = FirebaseAuth.instance;

  final storage = FlutterSecureStorage();

  Future<void> sendUserProfile(UserModel user) async {
    try {
      final response = await http.post(
        Uri.parse(_baseUrl),
        headers: <String, String>{
          'Content-Type': 'application/json; charset=UTF-8',
        },
        body: jsonEncode({
          'email': user.email,
          'phone': user.phone,
          'nickname': user.nickname,
          'profile': user.profilePicture,
          'googleAT': user.googleAccessToken,
        }),
      );

      print('Response from the backend: ${response.body}');

      if (response.statusCode == 200) {
        final Map<String, dynamic> responseData = json.decode(response.body);
        await storage.write(
            key: 'accessToken', value: responseData['accessToken']);
        await storage.write(
            key: 'refreshToken', value: response.headers['Set-Cookie']);
      } else {
        print('Error with status code: ${response.statusCode}');
        throw Exception(
            'Failed to load user profile. Status code: ${response.statusCode}');
      }
    } catch (e) {
      print('An error occurred while sending user profile: $e');
      // Here you might want to handle the error more gracefully
    }
  }

  //구글 로그인
  Future<UserModel?> signInWithGoogle() async {
    try {
      GoogleSignInAccount? googleUser = await _googleSignIn.signIn();
      if (googleUser == null) return null;

      GoogleSignInAuthentication googleAuth = await googleUser.authentication;

      OAuthCredential credential = GoogleAuthProvider.credential(
        accessToken: googleAuth.accessToken,
        idToken: googleAuth.idToken,
      );

      UserCredential userCredential =
          await _firebaseAuth.signInWithCredential(credential);
      User? user = userCredential.user;

      if (user != null) {
        UserModel userModel = UserModel(
          email: user.email,
          phone: user.phoneNumber,
          nickname: user.displayName,
          profilePicture: user.photoURL,
          googleAccessToken: googleAuth.accessToken,
        );
        await UserService().sendUserProfile(userModel);
        return userModel;
      }
    } catch (e) {
      print("Error signing in with Google: $e");
      return null;
    }
    return null;
  }

  
  //구글 로그아웃
  Future<void> signOut() async {
    await _googleSignIn.signOut();
    await _firebaseAuth.signOut();
  }
}
