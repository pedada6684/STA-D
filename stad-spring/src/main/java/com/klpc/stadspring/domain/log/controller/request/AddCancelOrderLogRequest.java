package com.klpc.stadspring.domain.log.controller.request;

import com.klpc.stadspring.domain.log.service.command.AddCancelOrderLogCommand;
import com.klpc.stadspring.domain.log.service.command.AddOrderLogCommand;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddCancelOrderLogRequest {
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
