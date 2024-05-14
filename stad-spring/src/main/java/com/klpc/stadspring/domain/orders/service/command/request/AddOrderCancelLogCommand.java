package com.klpc.stadspring.domain.orders.service.command.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AddOrderCancelLogCommand {
    private Long advertId;
    private Long userId;
    private Long orderId;
    private Long contentId;
    private Long productId;
    private Long price;
    private Boolean status;
    private LocalDateTime regDate;
    private LocalDateTime updateDate;
}
