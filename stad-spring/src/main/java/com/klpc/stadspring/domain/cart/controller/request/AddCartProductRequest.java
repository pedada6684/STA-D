package com.klpc.stadspring.domain.cart.controller.request;

import com.klpc.stadspring.domain.cart.entity.CartProduct;
import com.klpc.stadspring.domain.cart.service.command.AddProductToCartCommand;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddCartProductRequest {
    private Long userId;
    private List<CartProductPostRequest> cartProductList;

    public AddProductToCartCommand toCommand() {
        return AddProductToCartCommand.builder()
                .userId(userId)
                .cartProductList(cartProductList)
                .build();
    }
}
