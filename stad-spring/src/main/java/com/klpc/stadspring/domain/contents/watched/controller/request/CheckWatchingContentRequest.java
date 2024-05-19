package com.klpc.stadspring.domain.contents.watched.controller.request;

import com.klpc.stadspring.domain.contents.watched.service.command.request.CheckWatchingContentCommand;
import lombok.Getter;

@Getter
public class CheckWatchingContentRequest {
    private Long userId;
    private Long detailId;

    public CheckWatchingContentCommand toCommand() {
        return CheckWatchingContentCommand.builder()
                .userId(userId)
                .detailId(detailId)
                .build();
    }
}
