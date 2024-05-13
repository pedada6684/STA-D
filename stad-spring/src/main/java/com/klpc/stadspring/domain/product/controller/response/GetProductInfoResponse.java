package com.klpc.stadspring.domain.product.controller.response;

import com.klpc.stadspring.domain.image.product_image.entity.ProductImage;
import com.klpc.stadspring.domain.product.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetProductInfoResponse {

    /**
     *  상품 정보 조회
     */

    private Long productId;
    private String name;
    private List<String> images;
    private String thumbnail;
    private Long cityDeliveryFee;
    private Long mtDeliveryFee;
    private String expStart;
    private String expEnd;
    private List<GetProductInfoProductTypeResponse> productTypeList;

}


