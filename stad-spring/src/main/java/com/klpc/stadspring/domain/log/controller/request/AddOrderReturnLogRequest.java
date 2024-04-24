package com.klpc.stadspring.domain.log.controller.request;

import com.klpc.stadspring.domain.log.service.command.AddOrderReturnLogCommand;
import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddOrderReturnLogRequest {
    private Long orderId;
    private Long userId;

    public AddOrderReturnLogCommand toCommand() {
        return AddOrderReturnLogCommand.builder()
                .orderId(orderId)
                .userId(userId)
                .regDate(LocalDateTime.now())
                .build();
    }
}
