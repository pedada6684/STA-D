package com.klpc.stadspring.domain.cart.service.command;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddProductToCartCommand {
    /**
     * 카트 물건 추가 요청
     */

    private Long userId;
    private Long cartId;
    private Long productId;
    private Long quantity;
    private Long adverseId;
    private Long contentId;
}
