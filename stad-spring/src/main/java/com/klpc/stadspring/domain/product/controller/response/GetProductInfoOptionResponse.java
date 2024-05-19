package com.klpc.stadspring.domain.product.controller.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetProductInfoOptionResponse {

    Long optionId;
    String optionName;
    Long optionValue;

}
