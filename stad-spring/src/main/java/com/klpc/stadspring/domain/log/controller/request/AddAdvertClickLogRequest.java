package com.klpc.stadspring.domain.log.controller.request;

import com.klpc.stadspring.domain.log.service.command.AddAdvertClickLogCommand;
import com.klpc.stadspring.domain.option.service.command.AddOptionCommand;
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
