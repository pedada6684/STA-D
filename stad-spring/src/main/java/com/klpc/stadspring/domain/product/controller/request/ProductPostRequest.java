package com.klpc.stadspring.domain.product.controller.request;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductPostRequest {

    /**
     *  상품 등록 요청
     */

    String name;
    Long price;
    Long quantity;
    String introduction;
    String thumbnail;
    String category;
    LocalDateTime sellStart;
    LocalDateTime sellEnd;
    Long cityDeliveryFee;
    Long mtDeliveryFee;
    LocalDateTime expStart;
    LocalDateTime expEnd;
    LocalDateTime deliveryDate;
}
