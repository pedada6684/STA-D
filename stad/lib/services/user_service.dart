import 'dart:convert';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:google_sign_in/google_sign_in.dart';
import 'package:http/http.dart' as http;

class UserService {
  final FirebaseAuth _firebaseAuth = FirebaseAuth.instance;
  final GoogleSignIn _googleSignIn = GoogleSignIn();

  // final String uri = 'http://10.0.2.2:8080/api/v1/auth/applogin';
  // final uri = Uri.parse(
  //     'http://10.0.2.2:8080/api/v1/auth/applogin?email=string&phone=string&nickname=string&profile=string&googleAT=string');

  Future<User?> signInWithGoogle() async {
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
      await sendUserProfile(user, googleAuth.accessToken);
      print('User signed in : ${user}');
      return user;
    }
    return null;
  }

  Future<void> sendUserProfile(User user, String? googleAccessToken) async {
    try {
      final dynamic userProfile = {
        'email': user.email?.toString() ?? '',
        'phone': user.phoneNumber?.toString() ?? '',
        'nickname': user.displayName?.toString() ?? '',
        'profile': user.photoURL?.toString() ?? '',
        'googleAT': googleAccessToken?.toString() ?? '',
      };

      final response = await http.post(
        Uri.parse('http://10.0.2.2:8080/api/v1/auth/applogin'),
        headers: <String, String>{
          'Content-Type': 'application/json; charset=UTF-8',
        },
        body: json.encode(<String, String>{
          'email': user.email?.toString() ?? '',
          'phone': user.phoneNumber?.toString() ?? '',
          'nickname': user.displayName?.toString() ?? '',
          'profile': user.photoURL?.toString() ?? '',
          'googleAT': googleAccessToken?.toString() ?? '',
        }),
      );

      if (response.statusCode != 200) {
        throw Exception(
            'Failed to send user profile: Status code ${response.statusCode}, Body: ${response.body}');
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
