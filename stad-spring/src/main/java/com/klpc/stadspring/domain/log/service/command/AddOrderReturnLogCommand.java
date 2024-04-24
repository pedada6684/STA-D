package com.klpc.stadspring.domain.log.service.command;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AddOrderReturnLogCommand {
    private Long orderId;
    private Long userId;
    private LocalDateTime regDate;
}
