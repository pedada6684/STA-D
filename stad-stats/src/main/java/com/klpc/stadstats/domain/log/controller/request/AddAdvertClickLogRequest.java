package com.klpc.stadstats.domain.log.controller.request;

import com.klpc.stadstats.domain.log.service.command.AddAdvertClickLogCommand;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddAdvertClickLogRequest {
    private Long advertVideoId;
    private Long advertId;
    private Long userId;
    private Long contentId;

    public AddAdvertClickLogCommand toCommand() {
        return AddAdvertClickLogCommand.builder()
                .advertVideoId(advertVideoId)
                .advertId(advertId)
                .userId(userId)
                .contentId(contentId)
                .regDate(LocalDateTime.now())
                .build();
    }
}
