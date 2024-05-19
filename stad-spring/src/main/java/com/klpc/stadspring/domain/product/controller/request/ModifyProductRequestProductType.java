package com.klpc.stadspring.domain.product.controller.request;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ModifyProductRequestProductType {

    Long id;
    Long productTypeId;
    String productTypeName;
    Long productTypePrice;
    Long productTypeQuantity;
    List<ModifyProductRequestOption> options;

}
