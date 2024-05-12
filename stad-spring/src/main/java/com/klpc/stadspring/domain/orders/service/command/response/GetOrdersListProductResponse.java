package com.klpc.stadspring.domain.orders.service.command.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetOrdersListProductResponse {

    Long productId;
    String productName;

}
