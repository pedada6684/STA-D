package com.klpc.stadstats.domain.log.controller.event;

import com.klpc.stadstats.domain.log.service.command.AddCancelOrderLogCommand;
import com.klpc.stadstats.domain.log.service.command.AddOrderLogCommand;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AddOrderLogEvent {
    private Long advertId;
    private Long advertVideoId;
    private Long userId;
    private Long orderId;
    private Long contentId;
    private Long productId;
    private Long price;

    public AddOrderLogCommand toCommand() {
        return AddOrderLogCommand.builder().
                advertId(advertId).
                advertVideoId(advertVideoId).
                userId(userId).
                contentId(contentId).
                productId(productId).
                price(price).
                status(true).
                regDate(LocalDateTime.now()).
                updateDate(LocalDateTime.now()).
                build();
    }
}
