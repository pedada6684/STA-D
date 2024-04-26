import 'package:flutter_secure_storage/flutter_secure_storage.dart';

class AuthService {
  final storage = FlutterSecureStorage();

  Future<void> saveToken(String token) async {
    await storage.write(key: 'googleAccessToken', value: token);
  }

  Future<String?> getToken() async {
    return await storage.read(key: 'googleAccessToken');
  }

  Future<void> deleteToken() async {
    await storage.delete(key: 'googleAccessToken');
  }
}
