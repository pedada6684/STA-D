package com.klpc.stadspring.domain.product_review.service.command;

import com.klpc.stadspring.domain.product_review.controller.response.GetProductReviewResponse;
import com.klpc.stadspring.domain.product_review.entity.ProductReview;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class ProductReviewInfoCommand {
    private Long userId;
    private Long productId;
    private String title;
    private String content;
    private Double score;
    private String reviewImg;

    public static GetProductReviewResponse convertReviewInfoCommand(ProductReview productReview){
        return GetProductReviewResponse.builder()
                .userId(productReview.getId())
                .productId(productReview.getProduct().getId())
                .title(productReview.getTitle())
                .content(productReview.getContent())
                .score(productReview.getScore())
                .reviewImg(productReview.getReviewImg())
                .build();
    }
}
