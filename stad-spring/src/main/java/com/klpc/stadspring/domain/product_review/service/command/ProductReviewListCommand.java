package com.klpc.stadspring.domain.product_review.service.command;

import com.klpc.stadspring.domain.product_review.entity.ProductReview;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductReviewListCommand {
    private Long userId;
}
