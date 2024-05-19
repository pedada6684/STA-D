package com.klpc.stadalert.domain.alert.controller.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContentPlayEvent {
    private Long userId;
    private Long contentDetailId;
}