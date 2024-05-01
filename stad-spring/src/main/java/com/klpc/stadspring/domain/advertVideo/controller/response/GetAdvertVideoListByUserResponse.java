package com.klpc.stadspring.domain.advertVideo.controller.response;

import com.klpc.stadspring.domain.advertVideo.service.command.response.GetAdvertVideoListByUserResponseCommand;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GetAdvertVideoListByUserResponse {
    private List<GetAdvertVideoListByUserResponseCommand> data;
}
