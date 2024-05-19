package com.klpc.stadspring.global.event;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class AdvertsStartEvent {
    private Long userId;
    private Long contentDetailId;
    private List<Long> advertIdList;

    public AdvertsStartEvent(Long userId, Long contentDetailId, List<Long> advertIdList) {
        this.userId = userId;
        this.contentDetailId = contentDetailId;
        this.advertIdList = advertIdList;
    }
}