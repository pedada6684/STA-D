package com.klpc.stadspring.domain.orders.controller.request;

import lombok.Getter;

import java.util.List;

@Getter
public class AddOrdersProductTypeRequest {

    Long productTypeId;
    Long productCnt;
    Long optionId;

}
