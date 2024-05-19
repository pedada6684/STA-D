import 'package:firebase_auth/firebase_auth.dart';

class UserModel {
  final String? email;
  final String? phone;
  final String? nickname;
  final String? name;
  final String? profilePicture;
  final String? googleAccessToken;

  UserModel({
    this.email,
    this.phone,
    this.name,
    this.nickname,
    this.profilePicture,
    this.googleAccessToken,
  });

  factory UserModel.fromFirebaseUser(User user, String? googleAccessToken) {
    return UserModel(
      email: user.email,
      phone: user.phoneNumber,
      name: user.displayName,
      nickname: user.displayName,
      profilePicture: user.photoURL,
      googleAccessToken: googleAccessToken,
    );
  }

  UserModel copyWith({
    String? email,
    String? phone,
    String? nickname,
    String? name,
    String? profilePicture,
    String? googleAccessToken,
  }) {
    return UserModel(
      email: email ?? this.email,
      phone: phone ?? this.phone,
      name: name ?? this.name,
      nickname: nickname ?? this.nickname,
      profilePicture: profilePicture ?? this.profilePicture,
      googleAccessToken: googleAccessToken ?? this.googleAccessToken,
    );
  }

  factory UserModel.fromJson(Map<String, dynamic> json) {
    return UserModel(
      email: json['email'] as String?,
      phone: json['phone'] as String?,
      name: json['name'] as String?,
      nickname: json['nickname'] as String?,
      profilePicture: json['profile'] as String?,
      googleAccessToken: json['googleAT'] as String?,
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'email': email ?? '',
      'phone': phone ?? '',
      'name': name ?? '',
      'nickname': nickname ?? '',
      'profile': profilePicture ?? '',
      'googleAT': googleAccessToken ?? '',
    };
  }
}
