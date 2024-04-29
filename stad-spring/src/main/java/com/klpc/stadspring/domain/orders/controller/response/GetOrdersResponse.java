package com.klpc.stadspring.domain.orders.controller.response;

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
    List<Long> productTypeId;
    List<String> productTypeName;
    String productThumbnailUrl;

}
