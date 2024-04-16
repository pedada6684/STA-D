package com.klpc.stadspring.domain.product_review.service.command;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddReviewCommand {
    /**
     *  리뷰 작성 요청
     */

    private Long userId;
    private Long productId;
    private String title;
    private String content;
    private String regDate;
}
