package com.klpc.stadspring.domain.product.controller.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GetProductInfoProductTypeResponse {

    Long productTypeId;
    String productTypeName;
    Long productTypePrice;
    Long productTypeQuantity;
    List<GetProductInfoOptionResponse> options;

}
