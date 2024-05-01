package com.klpc.stadspring.domain.advertVideo.service.command.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetAdvertVideoListByUserResponseCommand {
    private Long videoId;
    private String videoUrl;
}
