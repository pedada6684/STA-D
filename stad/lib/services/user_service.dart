import 'dart:convert';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:google_sign_in/google_sign_in.dart';
import 'package:http/http.dart' as http;

class UserService {
  final FirebaseAuth _firebaseAuth = FirebaseAuth.instance;
  final GoogleSignIn _googleSignIn = GoogleSignIn();

  final uri = Uri.parse('http://10.0.2.2:8080/api/v1/auth/applogin');

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
      print(user.email);
      print(googleAccessToken);
      print(user.displayName);
      print(user.photoURL);
      print(user.email);

      final userProfile = <String, String>{
        'email': 'zlddmswl12@gmail.com',
        'phone': '',
        'nickname': '은지',
        'profile':
            'https://lh3.googleusercontent.com/a/ACg8ocJL0ngdcGWGcIM8OTRoq6_ypFhKtq_MW2-xbGa-Sc1ooMo0Ng=s96-c',
        'googleAT':
            'ya29.a0Ad52N39370bNJjo9Isa82kWPB_FLQtcaEQGUf2_6z1hGhojT_-dvdiKlCKkLN5ri_n_Bgc-Fka4SnQM1paPFF_tI5cPcdWOOCr0PLcb8u_aHIGCKOWK_b1TxaYWghNo2boiGt8sOlbQGs40lpq4xuAaYHUcfvwShGeMaCgYKAQASARISFQHGX2Miv4ABzMu3cg1SjgKYIb_9KQ0170',
      };
      // final userProfile = {
      //   'email': user.email?.toString() ?? '',
      //   'phone': user.phoneNumber?.toString() ?? '',
      //   'nickname': user.displayName?.toString() ?? '',
      //   'profile': user.photoURL?.toString() ?? '',
      //   'googleAT': googleAccessToken?.toString() ?? '',
      // };
      //
      print(userProfile);
      print(userProfile);
      print(userProfile);

      final response = await http.post(
        uri,
        headers: <String, String>{
          'Content-Type': 'application/json; charset=UTF-8',
        },
        body: json.encode(userProfile),
      );

      if (response.statusCode != 200) {
        throw Exception('Failed to send user profile: ${response.body}');
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
