package com.klpc.stadspring.domain.product.service.command;

import com.klpc.stadspring.domain.product.controller.response.GetProductInfoResponse;
import com.klpc.stadspring.domain.product.entity.Product;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class GetProductInfoCommand {
    /**
     *  상품 정보 조회
     */

    private Long id;
    private String name;
    private Long price;
    private Long quantity;
    private List<String> images;
    private String thumbnail;
    private String category;
    private String sellStart;
    private String sellEnd;
    private Long cityDeliveryFee;
    private Long mtDeliveryFee;
    private String expStart;
    private String expEnd;
    private String deliveryDate;

    public static GetProductInfoResponse ConvertProductInfoCommand(Product product){
        return GetProductInfoResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .thumbnail(product.getThumbnail())
                .category(product.getCategory())
                .sellStart(product.getSellStart())
                .sellEnd(product.getSellEnd())
                .cityDeliveryFee(product.getCityDeliveryFee())
                .mtDeliveryFee(product.getMtDeliveryFee())
                .expStart(product.getExpStart())
                .expEnd(product.getExpEnd())
                .deliveryDate(product.getDeliveryDate())
                .build();
    }
}
