package com.klpc.stadspring.domain.contents.detail.controller.request;

import com.klpc.stadspring.domain.contents.detail.service.command.request.AddDetailRequestCommand;
import lombok.Getter;

@Getter
public class AddDetailRequest {
    private int episode;
    private String videoUrl;
    private String summary;
    private Long contentConceptId;

    public AddDetailRequestCommand toCommand() {
        return AddDetailRequestCommand.builder()
                .episode(episode)
                .videoUrl(videoUrl)
                .summary(summary)
                .contentConceptId(contentConceptId)
                .build();
    }
}
