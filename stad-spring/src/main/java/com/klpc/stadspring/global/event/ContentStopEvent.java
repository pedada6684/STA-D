package com.klpc.stadspring.global.event;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ContentStopEvent {
    private Long userId;
    private Long contentId;

    public ContentStopEvent(Long userId, Long contentId) {
        this.userId = userId;
        this.contentId = contentId;
    }
}