package com.klpc.stadspring.domain.option.controller.response;

import com.klpc.stadspring.domain.option.entity.ProductOption;
import com.klpc.stadspring.domain.option.service.command.GetOptionCommand;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetOptionListByProductIdResponse {
    List<GetOptionCommand> list;
}
