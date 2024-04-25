package com.klpc.stadspring.domain.orders.service.command.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GetOrdersListResponseCommand {

    Long ordersId;
    String orderDate;
    String orderStatus;
    Long contentId;
    Long advertId;
    String deliveryStatus;
    List<Long> productId;
    List<String> productName;
    List<String> productThumbnailUrl;

}
