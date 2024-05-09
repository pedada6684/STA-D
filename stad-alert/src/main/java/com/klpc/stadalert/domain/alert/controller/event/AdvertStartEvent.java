package com.klpc.stadalert.domain.alert.controller.event;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdvertStartEvent {
    private Long advertId;
    private Long userId;
}
