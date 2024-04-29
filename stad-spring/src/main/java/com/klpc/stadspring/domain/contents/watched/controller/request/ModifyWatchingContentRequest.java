package com.klpc.stadspring.domain.contents.watched.controller.request;

import com.klpc.stadspring.domain.contents.watched.service.command.request.ModifyWatchingContentCommand;
import lombok.Getter;

@Getter
public class ModifyWatchingContentRequest {
    private Long userId;
    private Long detailId;
    private boolean status;
    private String stopTime;

    public ModifyWatchingContentCommand toCommand() {
        return ModifyWatchingContentCommand.builder()
                .userId(userId)
                .detailId(detailId)
                .status(status)
                .stopTime(stopTime)
                .build();
    }
}
