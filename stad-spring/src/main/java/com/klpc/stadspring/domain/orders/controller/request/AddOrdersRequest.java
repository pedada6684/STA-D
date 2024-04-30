package com.klpc.stadspring.domain.orders.controller.request;

import lombok.Getter;

import java.util.List;

@Getter
public class AddOrdersRequest {

    Long userId;
    List<AddOrdersProductTypeRequest> productTypes;
    Long contentId;
    Long advertId;
    String name;
    String phoneNumber;
    String locationName;
    String location;
    String locationDetail;
    String locationNum;

}
