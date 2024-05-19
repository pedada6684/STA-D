package com.klpc.stadalert.domain.alert.controller.event;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ContentStartEvent {
    private Long contentId;
    private Long userId;
}
