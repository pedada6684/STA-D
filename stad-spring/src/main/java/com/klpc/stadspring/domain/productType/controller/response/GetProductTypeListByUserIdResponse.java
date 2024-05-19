package com.klpc.stadspring.domain.productType.controller.response;

import com.klpc.stadspring.domain.productType.service.command.GetProductTypeListByUserIdCommand;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GetProductTypeListByUserIdResponse {

    List<GetProductTypeListByUserIdCommand> data;

}
