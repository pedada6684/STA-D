package com.klpc.stadspring.domain.product_review.service.command;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetProductReviewCommand {
    private Long userId;
    private Long productId;
    private String title;
    private String content;
    private Double score;
    private String reviewImg;
    private String regDate;
}
