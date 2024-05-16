package com.klpc.stadstats.domain.log.controller.event;

import com.klpc.stadstats.domain.log.service.command.AddCancelOrderLogCommand;
import com.klpc.stadstats.domain.log.service.command.AddOrderLogCommand;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddOrderLogEvent {
    private Long advertId;
    private Long userId;
    private Long orderId;
    private Long contentId;
    private Long productId;
    private Long price;

    public AddOrderLogCommand toCommand() {
        return AddOrderLogCommand.builder().
                advertId(advertId).
                userId(userId).
                orderId(orderId).
                contentId(contentId).
                productId(productId).
                price(price).
                status(true).
                regDate(LocalDateTime.now()).
                updateDate(LocalDateTime.now()).
                build();
    }
}
