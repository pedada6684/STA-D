package com.klpc.stadspring.domain.contents.detail.controller.response;

import com.klpc.stadspring.domain.contents.detail.service.command.response.GetPopularContentResponseCommand;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GetPopularContentResponse {
    private List<GetPopularContentResponseCommand> data;

    public static GetPopularContentResponse from(List<GetPopularContentResponseCommand> commandList) {
        return GetPopularContentResponse.builder()
                .data(commandList)
                .build();
    }
}
