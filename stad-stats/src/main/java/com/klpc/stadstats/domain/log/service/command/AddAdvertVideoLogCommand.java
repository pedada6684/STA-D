package com.klpc.stadstats.domain.log.service.command;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AddAdvertVideoLogCommand {
    private Long advertVideoId;
    private Long advertId;
    private Long userId;
    private Long contentId;
    private LocalDateTime regDate;
}
