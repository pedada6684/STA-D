package com.klpc.stadstream.global.event;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ContentStartEvnet {
    private Long userId;
    private Long contentId;

    public ContentStartEvnet(Long userId, Long contentId) {
        this.userId = userId;
        this.contentId = contentId;
    }
}