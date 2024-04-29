import 'package:dio/dio.dart';
import 'package:stad/constant/api.dart';

class AdService {
  final Dio dio = Dio();
  final url = Uri.parse('$locApi/advert/advert-info');

  //광고 정보 받아오기
  Future<void> getAdInfo(int advertId) async {
    try {
      final response = await dio.get('${url}?advertId=${advertId}');

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
}
