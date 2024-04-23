package com.klpc.stadspring.domain.product_review.controller.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetProductReviewListByUserIdRequest {
    private Long userId;
}
