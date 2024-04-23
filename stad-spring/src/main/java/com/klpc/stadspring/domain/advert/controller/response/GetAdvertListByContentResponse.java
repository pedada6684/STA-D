package com.klpc.stadspring.domain.advert.controller.response;

import com.klpc.stadspring.domain.advert.service.command.response.GetAdvertListByContentResponseCommand;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GetAdvertListByContentResponse {

    List<GetAdvertListByContentResponseCommand> data;

}
