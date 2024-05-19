package com.klpc.stadspring.domain.cart.service.command;

import com.klpc.stadspring.domain.productType.entity.ProductType;

public class GetCartProductInfoCommand {
    private Long cartProductId;
    private Long productId;
    private ProductType productType;
    private Long quantity;
    private Long advertId;
    private Long contentId;
    private String thumbnail;
}
