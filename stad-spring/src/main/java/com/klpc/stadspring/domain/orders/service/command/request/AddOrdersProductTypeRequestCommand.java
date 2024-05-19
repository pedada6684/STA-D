package com.klpc.stadspring.domain.orders.service.command.request;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class AddOrdersProductTypeRequestCommand {

    Long productTypeId;
    Long productCnt;
    Long optionId;
    Long contentId;
    Long advertId;

}
