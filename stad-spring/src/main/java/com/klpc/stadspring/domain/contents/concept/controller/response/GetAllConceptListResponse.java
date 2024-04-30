package com.klpc.stadspring.domain.contents.concept.controller.response;

import com.klpc.stadspring.domain.contents.concept.service.command.response.GetAllConceptResponseCommand;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GetAllConceptListResponse {
    List<GetAllConceptResponseCommand> data;
}
