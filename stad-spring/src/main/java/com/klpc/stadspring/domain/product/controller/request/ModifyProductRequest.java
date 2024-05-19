package com.klpc.stadspring.domain.product.controller.request;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class ModifyProductRequest {

    private Long productId;
    private String name;
    private List<String> imgs;
    private String thumbnail;
    private Long cityDeliveryFee;
    private Long mtDeliveryFee;
    private LocalDateTime expStart;
    private LocalDateTime expEnd;
    private List<ModifyProductRequestProductType> productTypeList;

}
