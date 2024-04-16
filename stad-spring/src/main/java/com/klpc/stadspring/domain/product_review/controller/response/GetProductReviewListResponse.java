package com.klpc.stadspring.domain.product_review.controller.response;

import com.klpc.stadspring.domain.product.controller.response.GetProductListByAdverseResponse;
import com.klpc.stadspring.domain.product.entity.Product;
import com.klpc.stadspring.domain.product_review.entity.ProductReview;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class GetProductReviewListResponse {
    List<ProductReview> reviewList;

    public static GetProductReviewListResponse from(List<ProductReview> list){
        return GetProductReviewListResponse.builder()
                .reviewList(list)
                .build();
    }
}
