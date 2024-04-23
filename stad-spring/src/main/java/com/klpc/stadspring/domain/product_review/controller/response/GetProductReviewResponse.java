package com.klpc.stadspring.domain.product_review.controller.response;

import com.klpc.stadspring.domain.product_review.entity.ProductReview;
import com.klpc.stadspring.domain.user.controller.response.GetMemberInfoResponse;
import com.klpc.stadspring.domain.user.entity.User;
import lombok.*;

import java.time.LocalDateTime;

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
    private LocalDateTime regDate;

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
