package com.klpc.stadspring.domain.contents.bookmark.controller.request;

import com.klpc.stadspring.domain.contents.bookmark.service.command.request.DeleteBookmarkRequsetCommand;
import lombok.Getter;

@Getter
public class DeleteBookmarkRequest {
    private Long userId;
    private Long conceptId;

    public DeleteBookmarkRequsetCommand toCommand() {
        return DeleteBookmarkRequsetCommand.builder()
                .userId(userId)
                .conceptId(conceptId)
                .build();
    }
}
