package com.klpc.stadspring.domain.orders.service.command.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AddOrderRequestCommand {

    Long userId;
    Long productTypeId;
    Long productTypeCnt;
    Long contentId;
    Long advertId;
    String name;
    String phoneNumber;
    String locationName;
    String location;
    String locationDetail;
    String locationNum;

}
