package com.klpc.stadspring.domain.orders.service.command.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetOrderListProductTypeResponseCommand {

    Long productTypeId;
    String productName;
    Long productCnt;
    Long productPrice;
    String productImg;
    Long optionId;
    String optionName;
    Long optionValue;

}
