package com.klpc.stadspring.domain.orders.controller.request;

import lombok.Getter;

@Getter
public class AddOrdersRequest {

    Long userId;
    Long productId;
    Long productCnt;
    Long contentId;
    Long advertId;
    String name;
    String phoneNumber;
    String locationName;
    String location;
    String locationDetail;
    String locationNum;

}
