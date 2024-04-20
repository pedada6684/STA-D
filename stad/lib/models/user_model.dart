import 'package:firebase_auth/firebase_auth.dart';

class UserModel {
  final String? email;
  final String? phone;
  final String? nickname;
  final String? profilePicture;
  final String? googleAccessToken;

  UserModel({
    this.email,
    this.phone,
    this.nickname,
    this.profilePicture,
    this.googleAccessToken,
  });

  factory UserModel.fromFirebaseUser(User user, String? googleAccessToken) {
    return UserModel(
      email: user.email,
      phone: user.phoneNumber,
      nickname: user.displayName,
      profilePicture: user.photoURL,
      googleAccessToken: googleAccessToken,
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'email': email ?? '',
      'phone': phone ?? '',
      'nickname': nickname ?? '',
      'profile': profilePicture ?? '',
      'googleAT': googleAccessToken ?? '',
    };
  }
}
