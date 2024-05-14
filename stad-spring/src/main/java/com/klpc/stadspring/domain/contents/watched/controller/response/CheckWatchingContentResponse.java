package com.klpc.stadspring.domain.contents.watched.controller.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CheckWatchingContentResponse {
    private boolean result;
    private Long stopTime;
}
