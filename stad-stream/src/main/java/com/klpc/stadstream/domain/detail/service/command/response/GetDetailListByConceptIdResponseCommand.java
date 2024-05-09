package com.klpc.stadstream.domain.detail.service.command.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetDetailListByConceptIdResponseCommand {
    private Long detailId;
    private Integer episode;
    private String videoUrl;
    private String summary;
}
