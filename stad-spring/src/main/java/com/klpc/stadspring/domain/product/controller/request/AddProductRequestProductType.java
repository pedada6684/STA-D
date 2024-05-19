package com.klpc.stadspring.domain.product.controller.request;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class AddProductRequestProductType {

    Long id;
    String productTypeName;
    Long productTypePrice;
    Long productTypeQuantity;
    List<AddProductRequestOption> options;

}
