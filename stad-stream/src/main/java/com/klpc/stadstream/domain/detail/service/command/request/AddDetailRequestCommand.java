package com.klpc.stadstream.domain.detail.service.command.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddDetailRequestCommand {
    private int episode;
    private String videoUrl;
    private String summary;
    private Long contentConceptId;
}
