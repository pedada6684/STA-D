import 'package:dio/dio.dart';
import 'package:stad/models/review_model.dart'; // Review 모델 클래스를 임포트합니다.

class ReviewService {
  final Dio _dio = Dio(); // Dio 인스턴스 생성

  // 서버로부터 사용자 리뷰 목록을 불러오는 메서드
  Future<List<Review>> fetchMyReviews(int userId) async {
    try {
      print('$userId');
      print('$userId');
      print('$userId');
      print('$userId');
      final response = await _dio
          .get('https://www.mystad.com/api/review/mypage/list?userId=$userId');
      if (response.statusCode == 200 && response.data != null) {
        // 서버 응답으로부터 Review 목록을 생성합니다.
        List<Review> reviews = List<Review>.from(
            response.data.map((data) => Review.fromJson(data)));
        return reviews; // Review 목록을 반환합니다.
      } else {
        throw Exception('Failed to load reviews');
      }
    } on DioError catch (e) {
      // DioError를 캐치하여 처리합니다.
      print('Error fetching reviews: ${e.message}');
      throw Exception('Error occurred while fetching reviews');
    }
  }
}
