package com.klpc.stadspring.domain.orders.controller.response;

import com.klpc.stadspring.domain.orders.service.command.response.GetOrderListProductTypeResponseCommand;
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
    Long contentId;
    Long advertId;
    List<GetOrderListProductTypeResponseCommand> productTypes;

}
