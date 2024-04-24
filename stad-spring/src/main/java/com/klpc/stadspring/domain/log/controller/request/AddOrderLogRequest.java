package com.klpc.stadspring.domain.log.controller.request;

import com.klpc.stadspring.domain.log.service.command.AddOrderLogCommand;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddOrderLogRequest {
    private Long advertVideoId;
    private Long userId;
    private Long contentId;
    private Long productId;
    private LocalDateTime updateDate;

    public AddOrderLogCommand toCommand() {
        return AddOrderLogCommand.builder().
                advertVideoId(advertVideoId).
                userId(userId).
                contentId(contentId).
                productId(productId).
                status(true).
                regDate(LocalDateTime.now()).
                updateDate(LocalDateTime.now()).
                build();
    }
}
