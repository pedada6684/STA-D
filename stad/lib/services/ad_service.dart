import 'package:dio/dio.dart';
import 'package:stad/constant/api.dart';
import 'package:stad/models/advert_model.dart';

class AdService {
  final Dio dio = Dio();
  final url = Uri.parse('$locApi/advert');

  //광고 정보 받아오기
  Future<void> getAdInfo(int advertId) async {
    try {
      final response = await dio.get('${url}/advert-info?advertId=${advertId}');

      if (response.statusCode == 200) {
        print('response 받아옴 광고데이터 : ${response.data}');
      } else {
        print('광고 데이터 서버 오류 ${response.statusCode}');
      }
    } on DioException catch (e) {
      if (e.response != null) {
        print('오류다 오류 : ${e.response}');
      } else {
        print('광고 정보 받아오기 오류 : ${e.message}');
      }
    }
  }

  Future<List<Advert>> fetchAdverts(int userId) async {
    try {
      final response = await dio.get('$url/get-list?userId=$userId');
      // print(response.data); //{data : []}

      if (response.statusCode == 200 && response.data != null) {
        List<dynamic> advertsData = response.data['data'] as List<dynamic>;
        List<Advert> adverts = advertsData.map((data) => Advert.fromJson(data)).toList();
        return adverts;
      } else {
        throw Exception('Failed to load adverts for user $userId');
      }
    } on DioException catch (e) {
      print('Error fetching adverts for user $userId: ${e.message}');
      throw Exception('Error occurred while fetching adverts for user $userId: ${e.message}');
    }
  }
}
