package com.klpc.stadspring.domain.advert.controller.response;

import com.klpc.stadspring.domain.advert.service.command.response.GetAdvertListByClickResponseCommand;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GetAdvertListByClickResponse {

    List<GetAdvertListByClickResponseCommand> data;

}
