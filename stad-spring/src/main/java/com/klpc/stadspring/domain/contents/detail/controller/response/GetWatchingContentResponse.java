package com.klpc.stadspring.domain.contents.detail.controller.response;

import com.klpc.stadspring.domain.contents.detail.service.command.response.GetWatchingContentResponseCommand;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GetWatchingContentResponse {
    private List<GetWatchingContentResponseCommand> detailList;

    public static GetWatchingContentResponse from(List<GetWatchingContentResponseCommand> list) {
        return GetWatchingContentResponse.builder()
                .detailList(list)
                .build();
    }
}
