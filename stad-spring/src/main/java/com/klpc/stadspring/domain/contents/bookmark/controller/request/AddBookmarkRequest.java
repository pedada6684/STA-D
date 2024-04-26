package com.klpc.stadspring.domain.contents.bookmark.controller.request;

import com.klpc.stadspring.domain.contents.bookmark.service.command.AddBookmarkRequestCommand;
import lombok.Getter;

@Getter
public class AddBookmarkRequest {
    private Long userId;
    private Long detailId;

    public AddBookmarkRequestCommand toCommand() {
        return AddBookmarkRequestCommand.builder()
                .userId(userId)
                .detailId(detailId)
                .build();
    }
}
