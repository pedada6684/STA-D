package com.klpc.stadalert.domain.alert.controller.event;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ContentStopEvent {
    private Long userId;
    private Long contentId;
}