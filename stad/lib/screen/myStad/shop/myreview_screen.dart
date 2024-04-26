import 'package:flutter/material.dart';
import 'package:stad/constant/colors.dart';
import 'package:stad/widget/app_bar.dart';
import 'package:stad/widget/expandable_text.dart';

class Review {
  final String? ImageUrl;
  final String productName;
  final String productOption;
  final String reviewDate;
  final double rating;
  final String reviewContent;

  Review(
    this.ImageUrl,
    this.productName,
    this.productOption,
    this.reviewDate,
    this.rating,
    this.reviewContent,
  );
}

class MyReviewScreen extends StatefulWidget {
  const MyReviewScreen({super.key});

  @override
  State<MyReviewScreen> createState() => _MyReviewScreenState();
}

class _MyReviewScreenState extends State<MyReviewScreen> {
  List<Review> reviews = [
    Review(
      'https://picsum.photos/30/30',
      "블랙후드",
      "S 그레이",
      "2024.04.24",
      5.0,
      "후드가 넉넉하고 디자인도 마음에 들어요. 착용감도 좋습니다! 후드가 넉넉하고 디자인도 마음에 들어요. 착용감도 좋습니다! 후드가 넉넉하고 디자인도 마음에 들어요. 착용감도 좋습니다! 후드가 넉넉하고 디자인도 마음에 들어요. 착용감도 좋습니다! 후드가 넉넉하고 디자인도 마음에 들어요. 착용감도 좋습니다! 후드가 넉넉하고 디자인도 마음에 들어요. 착용감도 좋습니다! 후드가 넉넉하고 디자인도 마음에 들어요. 착용감도 좋습니다!",
    ),
    Review(
      "https://picsum.photos/30/30",
      "Vintage Jeans",
      "32 Blue",
      "2024.03.15",
      4.0,
      "청바지 재질이 튼튼하고 색도 예쁩니다. 다만 조금 타이트한 감이 있네요.",
    ),
    Review(
      "https://picsum.photos/30/30",
      "Summer T-Shirt",
      "M White",
      "2024.02.10",
      3.5,
      "얇고 시원해서 여름에 잘 입을 것 같아요. 하지만 세탁 후 조금 줄었어요.",
    ),
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: mainWhite,
      appBar: CustomAppBar(
        title: '상품 리뷰',
        showBackButton: true,
        showHomeButton: true,
        progressValue: 0,
        isLoading: false,
      ),
      body: reviews.isNotEmpty
          ? ListView.builder(
              itemCount: reviews.length,
              itemBuilder: (context, index) {
                final review = reviews[index];
                return Column(
                  children: [
                    ReviewCard(review: review),
                    Divider(
                      height: 1,
                      color: mainGray,
                    ),
                  ],
                );
              },
            )
          : Center(
              child: Text(
                '리뷰가 없습니다.',
                textAlign: TextAlign.center,
                style: TextStyle(color: darkGray),
              ),
            ),
    );
  }
}

class ReviewCard extends StatelessWidget {
  final Review review;

  const ReviewCard({
    Key? key,
    required this.review,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Card(
      color: mainWhite,
      surfaceTintColor: mainWhite,
      elevation: 0,
      margin: const EdgeInsets.all(8.0),
      child: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(review.reviewDate),
            SizedBox(
              height: 16.0,
            ),
            Row(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                (review.ImageUrl != null)
                    ?
                    // Image.network(
                    //         review.ImageUrl!,
                    //         width: 60,
                    //         height: 60,
                    //         fit: BoxFit.cover,
                    //       )
                    Image.asset(
                        'assets/image/박지운.png',
                        width: 60,
                      )
                    : Image.asset('assets/image/박지운.png', width: 60),
                SizedBox(width: 16.0),
                Expanded(
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Text(
                        review.productName,
                        style: TextStyle(
                          fontWeight: FontWeight.bold,
                        ),
                      ),
                      SizedBox(height: 8.0),
                      Text(review.productOption),
                      SizedBox(height: 8.0),
                    ],
                  ),
                ),
              ],
            ),
            Padding(
              padding: const EdgeInsets.symmetric(
                vertical: 8.0,
              ),
              child: Padding(
                padding: const EdgeInsets.only(right: 8.0),
                child: Row(
                  children: List.generate(
                    5,
                    (index) => Padding(
                      padding: const EdgeInsets.only(right: 4.0),
                      child: Image.asset(
                        index < review.rating
                            ? 'assets/image/lank_star.png'
                            : 'assets/image/lank_star_outlined.png',
                        width: 20,
                        color: Colors.amber,
                      ),
                    ),
                  ),
                ),
              ),
            ),
            ExpandableText(
              text: review.reviewContent,
              maxLength: 100,
            ),
            SizedBox(
              height: 16.0,
            ),
            SizedBox(
              width: 63,
              height: 35,
              child: ElevatedButton(
                onPressed: () {},
                style: ElevatedButton.styleFrom(
                    backgroundColor: mainNavy,
                    shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(5.0),
                    ),
                    padding:
                        EdgeInsets.symmetric(horizontal: 16.0, vertical: 8.0)),
                child: Text(
                  '삭제',
                  style: TextStyle(color: mainWhite),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
