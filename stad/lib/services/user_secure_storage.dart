import 'package:flutter_secure_storage/flutter_secure_storage.dart';

class AuthService {
  final storage = FlutterSecureStorage();

  Future<void> saveToken(String token, String userId) async {
    await storage.write(key: 'accessToken', value: token);
    await storage.write(key: 'userId', value: userId);
  }

  Future<String?> getToken() async {
    return await storage.read(key: 'accessToken');
  }

  Future<void> saveUserId(String userId) async {
    await storage.write(key: 'userId', value: userId);
    print(userId);
    print(userId);
    print(userId);
    print(userId);
    print(userId);
    print(userId);
    print(userId);
    print(userId);
    print(userId);
    print(userId);
    print(userId);
    print(userId);
    print(userId);
    print(userId);
    print(userId);
    print(userId);
    print(userId);
    print(userId);
    print(userId);
  }

  Future<String?> getUserId() async {
    return await storage.read(key: 'userId');
  }

  Future<void> deleteToken() async {
    await storage.delete(key: 'accessToken');
    await storage.delete(key: 'userId');
  }
}
