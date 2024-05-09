import 'package:dio/dio.dart';
import 'package:stad/constant/api.dart';
import 'package:stad/models/contents_model.dart';

class ContentsService {
  final Dio dio = Dio();

  //콘텐츠 상세정보 TODO:서버에서 보내줄거임 sse
  Future<Content> fetchContentDetails(int detailId) async {
    try {
      final response = await dio.get('$svApi/contents-detail/2');
      // final response = await dio.get('http://192.168.31.202:8080/api/contents-detail/1');
      // final response = await dio.get('http://192.168.0.9:8080/api/contents-detail/1');
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

  //내가 본 콘텐츠 불러오기
  Future<List<Map<String, dynamic>>> fetchWatchedcontents(int userId) async {
    try {
      final response = await dio.get(
          // '$svApi/contents-detail/collections/watching',
          'http://192.168.31.202:8080/api/contents-detail/collections/watching',
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
}
