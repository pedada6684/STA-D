package com.klpc.stadspring.domain.cart.controller.response;

import com.klpc.stadspring.domain.cart.entity.CartProduct;
import com.klpc.stadspring.domain.option.entity.ProductOption;
import com.klpc.stadspring.domain.productType.entity.ProductType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class GetCartProductInfoResponse {
    private Long cartProductId;
    private Long productId;
    private String productName;
    private ProductType productType;
    private Long quantity;
    private Long advertId;
    private Long contentId;
    private String thumbnail;
    private ProductOption option;

}
