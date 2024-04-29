package com.klpc.stadspring.domain.contents.bookmark.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class CheckContentResponse {
    boolean isBookmarked;

    public static CheckContentResponse from(boolean response) {
        return CheckContentResponse.builder()
                .isBookmarked(response)
                .build();
    }
}
