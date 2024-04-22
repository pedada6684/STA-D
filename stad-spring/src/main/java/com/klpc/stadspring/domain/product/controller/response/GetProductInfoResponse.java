package com.klpc.stadspring.domain.product.controller.response;

import com.klpc.stadspring.domain.product.entity.Product;
import com.klpc.stadspring.domain.user.controller.response.GetMemberInfoResponse;
import com.klpc.stadspring.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private Long price;
    private Long quantity;
    private String introduction;
    private String thumbnail;
    private String category;

    public static GetProductInfoResponse from(Product product){
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


