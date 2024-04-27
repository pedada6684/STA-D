package com.klpc.stadspring.domain.log.service.command;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AddOrderLogCommand {
    private Long advertId;
    private Long advertVideoId;
    private Long userId;
    private Long orderId;
    private Long contentId;
    private Long productId;
    private Boolean status;
    private LocalDateTime regDate;
    private LocalDateTime updateDate;
}
