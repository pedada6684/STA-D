package com.klpc.stadspring.domain.orders.controller.request;

import lombok.Getter;

import java.util.List;

@Getter
public class AddOrdersRequest {

    Long userId;
    List<AddOrdersProductTypeRequest> productTypes;

}
