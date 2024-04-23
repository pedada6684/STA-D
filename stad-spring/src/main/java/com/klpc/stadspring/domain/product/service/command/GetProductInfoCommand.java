package com.klpc.stadspring.domain.product.service.command;

import com.klpc.stadspring.domain.product.controller.response.GetProductInfoResponse;
import com.klpc.stadspring.domain.product.entity.Product;
import lombok.Builder;
import lombok.Data;

import java.util.List;

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
    List<String> images;
    String thumbnail;
    String category;

    public static GetProductInfoResponse ConvertProductInfoCommand(Product product){
        return GetProductInfoResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .thumbnail(product.getThumbnail())
                .category(product.getCategory())
                .build();
    }
}
