import 'package:dio/dio.dart';
import 'package:stad/constant/api.dart';
import 'package:stad/models/advert_model.dart';

class AdService {
  final Dio dio = Dio();

  final url = Uri.parse('$svApi/advert');

  // final url = Uri.parse('http://172.29.40.139:8080/api/advert');
  // final String url = ('http://192.168.31.202:8080/api/advert');
  // final String url = 'http://192.168.0.9:8080/api/advert';

  //광고 정보 받아오기
  //TODO: advertId 수정
  Future<Map<String, dynamic>> getAdInfo(int advertId) async {
    try {
      final response = await dio.get('$url/get?advertId=3');
      // final response = await dio.get('$url/advert-info?advertId=$advertId');

      print(response.data);

      if (response.statusCode == 200) {
        // 응답이 성공적이라면 JSON 데이터를 반환
        return response.data;
      } else {
        // 실패 응답 처리
        throw Exception(
            'Failed to fetch advert with statusCode: ${response.statusCode}');
      }
    } on DioException catch (e) {
      throw Exception('Error fetching advert: ${e.message}');
    }
  }

  //인기 광고
  Future<List<Map<String, dynamic>>> getPopularAdInfo() async {
    try {
      final response = await dio.get('$url/get-list-by-click');

      print('get popular ad info : ${response.data}');
      if (response.statusCode == 200 && response.data != null) {
        return List<Map<String, dynamic>>.from(response.data['data']);
      } else
        throw Exception('인기 광고 정보 불러오기 실패 : ${response.statusCode}');
    } on DioException catch (e) {
      throw Exception('Error fetching popular advert : ${e.response}');
    }
  }

  //유저 시청한 광고
  Future<List<Advert>> fetchAdverts(int userId) async {
    try {
      final response = await dio.get('$url/get-list?userId=$userId');
      // print(response.data); //{data : []}

      if (response.statusCode == 200 && response.data != null) {
        List<dynamic> advertsData = response.data['data'] as List<dynamic>;
        List<Advert> adverts =
            advertsData.map((data) => Advert.fromJson(data)).toList();
        return adverts;
      } else {
        throw Exception('Failed to load adverts for user $userId');
      }
    } on DioException catch (e) {
      print('Error fetching adverts for user $userId: ${e.message}');
      throw Exception(
          'Error occurred while fetching adverts for user $userId: ${e.message}');
    }
  }

  //TODO: contentId 수정
  Future<List<Advert>> getAdvertsByContentId(int contentId) async {
    try {
      final response = await dio.get('$url/get-list-by-content?contentId=4');

      if (response.statusCode == 200 && response.data != null) {
        print('콘텐츠 관련 광고 : ${response.data}'); // {data:[]}

        List<dynamic> advertsData = response.data['data'] as List<dynamic>;
        List<Advert> adverts =
            advertsData.map((data) => Advert.fromJson(data)).toList();
        return adverts;
      } else {
        throw Exception('컨텐츠 관련 광고 로드 실패');
      }
    } on DioError catch (e) {
      print('컨텐츠 관련 광고를 불러오는 중 오류 발생: ${e.message}');
      throw Exception('컨텐츠 관련 광고를 불러오는 중 오류 발생: ${e.message}');
    }
  }
}
