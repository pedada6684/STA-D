import 'package:dio/dio.dart';
import 'package:stad/constant/api.dart';
import 'package:stad/models/advert_model.dart';

class AdService {
  final Dio dio = Dio();
  final url = Uri.parse('$svApi/advert');

  // 광고 정보 받아오기
  Future<List<Advert>> getAdsInfo(List<int> advertIds) async {
    try {
      final response = await dio.get(
        '$url/get?advertIds=${advertIds.join('&advertIds=')}',
      );
      print('광고 정보 받아오기: ${response.data}');
      if (response.statusCode == 200) {
        // 응답이 성공적이라면 JSON 데이터를 반환
        return (response.data['data'] as List)
            .map((ad) => Advert.fromJson(ad as Map<String, dynamic>))
            .toList();
      } else {
        // 실패 응답 처리
        throw Exception(
            'Failed to fetch adverts with statusCode: ${response.statusCode}');
      }
    } on DioError catch (e) {
      print('광고 정보 에러: ${e.response}');
      throw Exception('Error fetching adverts: ${e.message}');
    }
  }

  // 인기 광고
  Future<List<Advert>> getPopularAdInfo() async {
    try {
      final response = await dio.get('$url/get-list-by-click');

      print('인기 광고 정보: ${response.data}');
      if (response.statusCode == 200 && response.data != null) {
        // JSON 응답 데이터가 문자열이 아닌 Map 형태로 처리되도록 수정
        return (response.data['data'] as List)
            .map((ad) => Advert.fromJson(ad as Map<String, dynamic>))
            .toList();
      } else {
        throw Exception(
            'Failed to fetch popular adverts with statusCode: ${response.statusCode}');
      }
    } on DioError catch (e) {
      print('Error fetching popular adverts: ${e.response}');
      throw Exception('Error fetching popular adverts: ${e.message}');
    }
  }

  // 유저 시청한 광고
  Future<List<Advert>> fetchAdverts(int userId) async {
    try {
      final response =
          await dio.get('$url/get-list', queryParameters: {'userId': userId});
      if (response.statusCode == 200 && response.data != null) {
        return (response.data['data'] as List)
            .map((data) => Advert.fromJson(data as Map<String, dynamic>))
            .toList();
      } else {
        throw Exception('Failed to load adverts for user $userId');
      }
    } on DioError catch (e) {
      throw Exception('Error fetching adverts for user $userId: ${e.message}');
    }
  }

  // 콘텐츠 관련 광고
  Future<List<Advert>> getAdvertsByContentId(int contentId) async {
    try {
      final response =
          await dio.get('$url/get-list-by-content?contentId=$contentId');

      if (response.statusCode == 200 && response.data != null) {
        print('콘텐츠 관련 광고: ${response.data}');
        return (response.data['data'] as List)
            .map((data) => Advert.fromJson(data as Map<String, dynamic>))
            .toList();
      } else {
        throw Exception('Failed to load adverts for contentId $contentId');
      }
    } on DioError catch (e) {
      throw Exception(
          'Error fetching adverts for contentId $contentId: ${e.message}');
    }
  }
}
