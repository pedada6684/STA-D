package com.klpc.stadspring.domain.contents.bookmark.service.command.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AddBookmarkRequestCommand {
    private Long userId;
    private Long conceptId;
}
