package com.klpc.stadspring.domain.product.service.command;

import com.klpc.stadspring.domain.product.controller.response.GetProductInfoResponse;
import com.klpc.stadspring.domain.product.entity.Product;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetProductInfoCommand {
    /**
     *  상품 정보 조회
     */

    Long id;
    String name;
    Long price;
    Long quantity;
    String introduction;
    String thumbnail;
    String category;

    public static GetProductInfoResponse ConvertProductInfoCommand(Product product){
        return GetProductInfoResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .introduction(product.getIntroduction())
                .thumbnail(product.getThumbnail())
                .category(product.getCategory())
                .build();
    }
}
