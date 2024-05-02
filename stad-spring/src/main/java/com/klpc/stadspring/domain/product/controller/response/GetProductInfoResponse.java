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

    private Long id;
    private String name;
    private List<ProductImage> images;
    private String thumbnail;
    private Long cityDeliveryFee;
    private Long mtDeliveryFee;
    private LocalDateTime expStart;
    private LocalDateTime expEnd;

    public static GetProductInfoResponse from(Product product){
        return GetProductInfoResponse.builder()
                .id(product.getId())
                .images(product.getImages())
                .name(product.getName())
                .thumbnail(product.getThumbnail())
                .cityDeliveryFee(product.getCityDeliveryFee())
                .mtDeliveryFee(product.getMtDeliveryFee())
                .expStart(product.getExpStart())
                .expEnd(product.getExpEnd())
                .build();
    }
}


