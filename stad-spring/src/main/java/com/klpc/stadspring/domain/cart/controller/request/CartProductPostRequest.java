package com.klpc.stadspring.domain.cart.controller.request;

import com.klpc.stadspring.domain.cart.service.command.AddProductToCartCommand;
import com.klpc.stadspring.domain.product.service.command.UpdateProductInfoCommand;
import com.klpc.stadspring.domain.product_review.service.command.AddReviewCommand;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartProductPostRequest {

    /**
     *  리뷰 작성 요청
     */

    private Long userId;
    private Long cartId;
    private Long productId;
    private Long quantity;
    private Long adverseId;
    private Long contentId;

    public AddProductToCartCommand toCommand(){
        return AddProductToCartCommand.builder()
                .userId(userId)
                .cartId(cartId)
                .productId(productId)
                .quantity(quantity)
                .adverseId(adverseId)
                .contentId(contentId)
                .build();
    }
}