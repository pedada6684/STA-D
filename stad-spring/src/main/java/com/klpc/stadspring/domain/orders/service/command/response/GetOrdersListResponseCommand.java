package com.klpc.stadspring.domain.orders.service.command.response;

import com.klpc.stadspring.domain.orders.controller.response.GetOrdersListResponse;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GetOrdersListResponseCommand {

    Long ordersId;
    String orderDate;
    String orderStatus;
    List<GetOrdersListProductResponse> products;
    List<GetOrderListProductTypeResponseCommand> productTypes;

}
