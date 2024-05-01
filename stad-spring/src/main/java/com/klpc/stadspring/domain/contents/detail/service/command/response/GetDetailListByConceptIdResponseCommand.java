package com.klpc.stadspring.domain.contents.detail.service.command.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetDetailListByConceptIdResponseCommand {
    private Integer episode;
    private String videoUrl;
    private String summary;
}
