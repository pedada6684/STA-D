package com.klpc.stadspring.domain.log.controller.request;

import com.klpc.stadspring.domain.log.service.command.AddAdvertClickLogCommand;
import com.klpc.stadspring.domain.log.service.command.AddAdvertVideoLogCommand;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddAdvertVideoLogRequest {
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
