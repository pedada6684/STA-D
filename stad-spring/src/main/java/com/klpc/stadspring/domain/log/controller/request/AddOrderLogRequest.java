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
    private Long advertId;
    private Long advertVideoId;
    private Long userId;
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
