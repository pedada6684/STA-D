package com.klpc.stadspring.domain.orders.service.command.request;

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
    private Long price;
}
