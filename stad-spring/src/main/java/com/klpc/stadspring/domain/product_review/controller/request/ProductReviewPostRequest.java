package com.klpc.stadspring.domain.product_review.controller.request;

import com.klpc.stadspring.domain.product.service.command.UpdateProductInfoCommand;
import com.klpc.stadspring.domain.product_review.service.command.AddReviewCommand;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductReviewPostRequest {

    /**
     *  리뷰 작성 요청
     */

    private Long userId;
    private Long productId;
    private String title;
    private String content;

    public AddReviewCommand toCommand(){
        return AddReviewCommand.builder()
                .userId(userId)
                .productId(productId)
                .title(title)
                .content(content)
                .build();
    }
}
