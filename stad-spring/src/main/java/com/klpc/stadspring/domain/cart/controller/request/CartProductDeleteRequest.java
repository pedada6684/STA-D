package com.klpc.stadspring.domain.cart.controller.request;

import com.klpc.stadspring.domain.cart.service.command.AddProductToCartCommand;
import com.klpc.stadspring.domain.cart.service.command.DeleteProductInCartCommand;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartProductDeleteRequest {
    Long cartProductId;

    public DeleteProductInCartCommand toCommand() {
        return DeleteProductInCartCommand.builder().
                cartProductId(cartProductId).build();
    }
}
