package com.klpc.stadspring.domain.product_review.service.command;

import com.klpc.stadspring.domain.product_review.controller.response.GetProductReviewResponse;
import com.klpc.stadspring.domain.product_review.entity.ProductReview;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductReviewInfoCommand {
    private Long userId;
    private Long productId;
    private String title;
    private String content;

    public static GetProductReviewResponse convertReviewInfoCommand(ProductReview productReview){
        return GetProductReviewResponse.builder()
                .userId(productReview.getId())
                .productId(productReview.getProduct().getId())
                .title(productReview.getTitle())
                .content(productReview.getContent())
                .build();
    }
}
