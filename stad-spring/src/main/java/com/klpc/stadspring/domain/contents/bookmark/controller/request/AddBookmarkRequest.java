package com.klpc.stadspring.domain.contents.bookmark.controller.request;

import com.klpc.stadspring.domain.contents.bookmark.service.command.request.AddBookmarkRequestCommand;
import lombok.Getter;

@Getter
public class AddBookmarkRequest {
    private Long userId;
    private Long conceptId;

    public AddBookmarkRequestCommand toCommand() {
        return AddBookmarkRequestCommand.builder()
                .userId(userId)
                .conceptId(conceptId)
                .build();
    }
}
