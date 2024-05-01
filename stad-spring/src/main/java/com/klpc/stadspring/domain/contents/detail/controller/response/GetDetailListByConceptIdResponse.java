package com.klpc.stadspring.domain.contents.detail.controller.response;

import com.klpc.stadspring.domain.contents.detail.service.command.response.GetDetailListByConceptIdResponseCommand;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GetDetailListByConceptIdResponse {
    List<GetDetailListByConceptIdResponseCommand> data;
}
