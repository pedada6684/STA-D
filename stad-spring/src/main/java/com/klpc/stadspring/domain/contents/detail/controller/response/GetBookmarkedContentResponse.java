package com.klpc.stadspring.domain.contents.detail.controller.response;

import com.klpc.stadspring.domain.contents.detail.service.command.response.GetBookmarkedContentResponseCommand;
import com.klpc.stadspring.domain.contents.detail.service.command.response.GetWatchingAndWatchedContentResponseCommand;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GetBookmarkedContentResponse {
    private List<GetBookmarkedContentResponseCommand> detailList;

    public static GetBookmarkedContentResponse from(List<GetBookmarkedContentResponseCommand> list) {
        return GetBookmarkedContentResponse.builder()
                .detailList(list)
                .build();
    }
}
