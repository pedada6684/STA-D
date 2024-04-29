package com.klpc.stadspring.domain.cart.service.command;

import com.klpc.stadspring.domain.cart.controller.request.CartProductPostRequest;
import com.klpc.stadspring.domain.cart.entity.CartProduct;
import lombok.*;

import java.util.List;

@Data
@Builder
public class AddProductToCartCommand {
    /**
     * 카트 물건 추가 요청
     */
    private Long userId;
    private List<CartProductPostRequest> cartProductList;

}
