package com.klpc.stadstats.domain.log.controller.event;

import com.klpc.stadstats.domain.log.service.command.AddCancelOrderLogCommand;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AddOrderCancelLogEvent {
    private Long advertId;
    private Long userId;
    private Long orderId;
    private Long contentId;
    private Long productId;
    private Long price;
    private Boolean status;
    private LocalDateTime regDate;
    private LocalDateTime updateDate;

    public AddCancelOrderLogCommand toCommand() {
        return AddCancelOrderLogCommand.builder().
                advertId(advertId).
                userId(userId).
                contentId(contentId).
                productId(productId).
                price(price * -1).
                status(false).
                regDate(LocalDateTime.now()).
                updateDate(LocalDateTime.now()).
                build();
    }
}
