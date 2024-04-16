package com.klpc.stadspring.domain.cart.controller.response;

import com.klpc.stadspring.domain.cart.entity.CartProduct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class GetCartProductInfoResponse {
    Long cartProductId;
    Long cartId;
    Long productId;
    Long quantity;
    Long adverseId;
    Long contentId;

    public static GetCartProductInfoResponse from(CartProduct cartProduct){
        return GetCartProductInfoResponse.builder().
                cartProductId(cartProduct.getId()).
                cartId(cartProduct.getCart().getId()).
                productId(cartProduct.getProduct().getId()).
                quantity(cartProduct.getQuantity()).
                adverseId(cartProduct.getAdverseId()).
                contentId(cartProduct.getContentId()).
                build();
    }
}
