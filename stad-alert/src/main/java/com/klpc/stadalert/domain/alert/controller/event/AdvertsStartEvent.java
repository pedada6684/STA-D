package com.klpc.stadalert.domain.alert.controller.event;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class AdvertsStartEvent {
    private Long userId;
    private Long contentDetailId;
    private List<Long> advertIdList;
}
