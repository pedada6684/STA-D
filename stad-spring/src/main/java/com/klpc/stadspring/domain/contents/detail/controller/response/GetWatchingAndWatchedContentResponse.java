package com.klpc.stadspring.domain.contents.detail.controller.response;

import com.klpc.stadspring.domain.contents.detail.service.command.response.GetWatchingAndWatchedContentResponseCommand;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GetWatchingAndWatchedContentResponse {
    private List<GetWatchingAndWatchedContentResponseCommand> detailList;

    public static GetWatchingAndWatchedContentResponse from(List<GetWatchingAndWatchedContentResponseCommand> list) {
        return GetWatchingAndWatchedContentResponse.builder()
                .detailList(list)
                .build();
    }
}
