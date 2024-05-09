package com.klpc.stadspring.domain.advert.service.command.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetAdvertAdvertVideo {

    Long advertVideoId;
    String advertVideoUrl;

}
