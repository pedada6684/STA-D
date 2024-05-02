import 'package:dio/dio.dart';
import 'package:stad/constant/api.dart';
import 'package:stad/models/review_model.dart'; // Review 모델 클래스를 임포트합니다.

class ReviewService {
  final Dio _dio = Dio(); // Dio 인스턴스 생성
  final reviewUrl = '$svApi/review';

  // 사용자 리뷰 목록을 불러오는 메서드
  Future<List<Review>> fetchMyReviews(int userId) async {
    try {
      final response = await _dio.get('$reviewUrl/list/user/$userId');
      print('fetchMyReviews response: ${response.data}');  // 응답 데이터 {reviewList : []}

      if (response.statusCode == 200 && response.data != null) {
        List<dynamic> reviewsList = response.data['reviewList'];  // 'reviewList' 키로 리스트 접근
        List<Review> reviews = reviewsList.map((data) => Review.fromJson(data)).toList();
        return reviews; // Review 목록을 반환
      } else {
        throw Exception('리뷰 받아오기 실패');
      }
    } on DioError catch (e) {
      print('Error fetching reviews: ${e.message}');
      throw Exception('리뷰 받아오다가 에러 났음');
    }
  }
}
