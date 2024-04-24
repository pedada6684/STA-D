package com.klpc.stadspring.domain.cart.controller.request;

import com.klpc.stadspring.domain.cart.service.command.AddCartProductCommand;
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


    private Long productId;
    private Long quantity;
    private Long advertId;
    private Long contentId;

    public AddCartProductCommand toCommand(){
        return AddCartProductCommand.builder()
                .productId(productId)
                .quantity(quantity)
                .advertId(advertId)
                .contentId(contentId)
                .build();
    }
}