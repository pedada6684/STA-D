package com.klpc.stadspring.domain.orders.controller.response;

import com.klpc.stadspring.domain.orders.service.command.response.GetOrderListProductTypeResponseCommand;
import com.klpc.stadspring.domain.orders.service.command.response.GetOrdersListProductResponse;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Getter
@Builder
public class GetOrdersResponse {

    Long ordersId;
    String orderDate;
    String orderStatus;
    List<GetOrdersListProductResponse> products;
    List<GetOrderListProductTypeResponseCommand> productTypes;

}
