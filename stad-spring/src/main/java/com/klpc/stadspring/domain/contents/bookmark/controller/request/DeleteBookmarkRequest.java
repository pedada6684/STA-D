package com.klpc.stadspring.domain.contents.bookmark.controller.request;

import com.klpc.stadspring.domain.contents.bookmark.service.command.DeleteBookmarkRequsetCommand;
import lombok.Getter;

@Getter
public class DeleteBookmarkRequest {
    private Long userId;
    private Long detailId;

    public DeleteBookmarkRequsetCommand toCommand() {
        return DeleteBookmarkRequsetCommand.builder()
                .userId(userId)
                .detailId(detailId)
                .build();
    }
}
