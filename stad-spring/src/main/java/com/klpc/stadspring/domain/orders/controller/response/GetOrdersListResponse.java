package com.klpc.stadspring.domain.orders.controller.response;

import com.klpc.stadspring.domain.orders.service.command.response.GetOrdersListResponseCommand;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GetOrdersListResponse {

    List<GetOrdersListResponseCommand> data;

}
