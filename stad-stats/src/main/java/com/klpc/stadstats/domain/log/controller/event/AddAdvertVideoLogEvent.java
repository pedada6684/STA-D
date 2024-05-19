package com.klpc.stadstats.domain.log.controller.event;

import com.klpc.stadstats.domain.log.service.command.AddAdvertVideoLogCommand;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddAdvertVideoLogEvent {
    private Long advertVideoId;
    private Long advertId;
    private Long userId;
    private Long contentId;

    public AddAdvertVideoLogCommand toCommand() {
        return AddAdvertVideoLogCommand.builder()
                .advertVideoId(advertVideoId)
                .advertId(advertId)
                .userId(userId)
                .contentId(contentId)
                .regDate(LocalDateTime.now())
                .build();
    }
}
