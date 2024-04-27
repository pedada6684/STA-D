package com.klpc.stadspring.domain.product_review.service.command;

import com.klpc.stadspring.domain.product_review.controller.response.GetProductReviewResponse;
import com.klpc.stadspring.domain.product_review.entity.ProductReview;
import com.klpc.stadspring.domain.user.entity.User;
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

}
