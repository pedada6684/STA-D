import 'package:dio/dio.dart';
import 'package:stad/constant/api.dart';
import 'package:stad/models/contents_model.dart';
import 'package:stad/services/alert_service.dart';

class ContentsService {
  final Dio dio = Dio();
  final AlertService alertService = AlertService();

  // 콘텐츠 상세정보
  Future<Content> fetchContentDetails(int contentId) async {
    try {
      final response = await dio.get('$svApi/contents-detail/$contentId');
      print('${response.data}');

      if (response.statusCode == 200) {
        return Content.fromJson(response.data);
      } else {
        throw Exception('Failed to load content details');
      }
    } catch (e) {
      print('Error fetching content details: $e');
      throw Exception('Error occurred while fetching content details');
    }
  }

  // 내가 본 콘텐츠 불러오기
  Future<List<Map<String, dynamic>>> fetchWatchedcontents(int userId) async {
    try {
      final response = await dio.get(
          '$svApi/contents-detail/collections/watching',
          queryParameters: {'userId': userId});

      print('내가 본 콘텐츠 목록 불러오기:${response.data}');
      if (response.statusCode == 200 && response.data != null) {
        List<dynamic> contentsData = response.data['content'] ?? [];

        return contentsData
            .map((data) => {
                  'title': data['title'],
                  'thumbnailUrl': data['thumbnailUrl'],
                  'detailId': data['detailId'],
                })
            .toList();
      } else {
        throw Exception('사용자 시청 콘텐츠 목록 불러오기 실패 $userId');
      }
    } on DioException catch (e) {
      print('Error fetching watched contents for user $userId: ${e.message}');
      throw Exception('콘텐츠 목록 불러오다가 에러: ${e.message}');
    }
  }

  // 처음 HomeScreen 들어올 때
  Future getCurrViewContent(int userId) async {
    try {
      final response = await dio.get('$svApi/contents-watch/now',
          queryParameters: {'userId': userId});
      print('getCurrViewContent : ${response.data}');
      if (response.statusCode == 200 && response.data != null) {
        return response.data['contentDetailId'];
      } else if (response.statusCode == 404) {
        return await fetchPopularContent();
      } else {
        throw Exception('Failed to get current content');
      }
    } catch (e) {
      print('Error getting current view content: $e');
      throw Exception('Error occurred while getting current view content');
    }
  }

  Future<List<Map<String, dynamic>>> fetchPopularContent() async {
    try {
      final response = await dio.get('$svApi/contents-concept/popular');
      if (response.statusCode == 200 && response.data != null) {
        if (response.data is List) {
          print(response.data);
          return List<Map<String, dynamic>>.from(response.data);
        } else if (response.data is Map) {
          List<dynamic> popularContentData = response.data['data'] ?? [];
          print(response.data);
          return popularContentData
              .map((data) => Map<String, dynamic>.from(data))
              .toList();
        } else {
          throw Exception('Unexpected data format');
        }
      } else {
        throw Exception('Failed to fetch popular content');
      }
    } catch (e) {
      print('Error fetching popular content: $e');
      throw Exception('Error occurred while fetching popular content');
    }
  }
}
