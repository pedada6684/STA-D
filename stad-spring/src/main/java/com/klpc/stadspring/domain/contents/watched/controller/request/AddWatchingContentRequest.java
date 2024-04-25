package com.klpc.stadspring.domain.contents.watched.controller.request;

import com.klpc.stadspring.domain.contents.watched.service.command.request.AddWatchingContentCommand;
import lombok.Getter;

@Getter
public class AddWatchingContentRequest {
    private Long userId;
    private Long detailId;

    public AddWatchingContentCommand toCommand() {
        return AddWatchingContentCommand.builder()
                .userId(userId)
                .detailId(detailId)
                .build();
    }
}
