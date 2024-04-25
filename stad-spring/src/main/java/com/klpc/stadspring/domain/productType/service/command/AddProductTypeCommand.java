package com.klpc.stadspring.domain.productType.service.command;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddProductTypeCommand {
    /**
     *  상품 타입 등록
     */
    Long productId;
    String name;
    Long price;
    Long quantity;

}
