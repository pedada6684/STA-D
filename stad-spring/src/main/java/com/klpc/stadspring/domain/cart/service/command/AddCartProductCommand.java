package com.klpc.stadspring.domain.cart.service.command;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddCartProductCommand {
    private Long userId;
    private Long productTypeId;
    private Long quantity;
    private Long advertId;
    private Long contentId;
    private Long optionId;
}