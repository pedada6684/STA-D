package com.klpc.stadspring.domain.orders.service.command.request;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class AddOrderRequestCommand {

    Long userId;
    List<AddOrdersProductTypeRequestCommand> addOrdersProductTypeRequestCommands;
    Long contentId;
    Long advertId;
    String name;
    String phoneNumber;
    String locationName;
    String location;
    String locationDetail;
    String locationNum;

}
