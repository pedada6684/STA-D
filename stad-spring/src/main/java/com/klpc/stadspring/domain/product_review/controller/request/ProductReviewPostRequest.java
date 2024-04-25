package com.klpc.stadspring.domain.product_review.controller.request;

import com.klpc.stadspring.domain.product.service.command.UpdateProductInfoCommand;
import com.klpc.stadspring.domain.product_review.service.command.AddReviewCommand;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

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
    private Double score;
    private MultipartFile reviewImg;

    public AddReviewCommand toCommand(){
        return AddReviewCommand.builder()
                .userId(userId)
                .productId(productId)
                .title(title)
                .content(content)
                .score(score)
                .reviewImg(reviewImg)
                .build();
    }
}
