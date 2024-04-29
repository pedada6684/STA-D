package com.klpc.stadspring.domain.product_review.controller.response;

import com.klpc.stadspring.domain.product_review.entity.ProductReview;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetProductReviewResponse {
    private Long userId;
    private Long productId;
    private String title;
    private String content;
    private Double score;
    private String reviewImg;
    private String regDate;

    public static GetProductReviewResponse from(ProductReview productReview){
        return GetProductReviewResponse.builder()
                .userId(productReview.getUser().getId())
                .productId(productReview.getProduct().getId())
                .title(productReview.getTitle())
                .content(productReview.getContent())
                .score(productReview.getScore())
                .reviewImg(productReview.getReviewImg())
                .regDate(productReview.getRegDate())
                .build();
    }
}
