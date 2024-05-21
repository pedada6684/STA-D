package com.klpc.stadstream.global.event;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdvertStartEvent {
    private Long advertVideoId;
    private Long advertId;
    private Long userId;
    private Long contentId;

    public AdvertStartEvent(Long advertVideoId, Long advertId, Long userId, Long contentId) {
        this.advertVideoId = advertVideoId;
        this.advertId = advertId;
        this.userId = userId;
        this.contentId = contentId;
    }
}