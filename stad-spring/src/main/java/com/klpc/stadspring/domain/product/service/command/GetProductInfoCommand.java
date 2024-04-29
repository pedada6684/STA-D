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
    private List<String> images;
    private String thumbnail;
    private Long cityDeliveryFee;
    private Long mtDeliveryFee;
    private String expStart;
    private String expEnd;

    public static GetProductInfoResponse ConvertProductInfoCommand(Product product){
        return GetProductInfoResponse.builder()
                .id(product.getId())
                .thumbnail(product.getThumbnail())
                .cityDeliveryFee(product.getCityDeliveryFee())
                .mtDeliveryFee(product.getMtDeliveryFee())
                .expStart(product.getExpStart())
                .expEnd(product.getExpEnd())
                .build();
    }
}
