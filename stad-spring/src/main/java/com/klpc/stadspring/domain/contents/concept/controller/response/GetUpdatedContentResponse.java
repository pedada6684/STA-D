package com.klpc.stadspring.domain.contents.concept.controller.response;

import com.klpc.stadspring.domain.contents.concept.service.command.response.GetUpdatedContentResponseCommand;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GetUpdatedContentResponse {
    private List<GetUpdatedContentResponseCommand> detailList;

    public static GetUpdatedContentResponse from(List<GetUpdatedContentResponseCommand> list) {
        return GetUpdatedContentResponse.builder()
                .detailList(list)
                .build();
    }
}
