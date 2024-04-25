package com.klpc.stadspring.domain.orders.service.command.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AddOrderRequestCommand {

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
