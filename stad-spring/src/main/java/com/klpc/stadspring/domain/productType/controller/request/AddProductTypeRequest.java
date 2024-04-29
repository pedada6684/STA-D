package com.klpc.stadspring.domain.productType.controller.request;

import com.klpc.stadspring.domain.productType.service.command.AddProductTypeCommand;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddProductTypeRequest {
    private Long productId;
    private String name;
    private Long price;
    private Long quantity;

    public AddProductTypeCommand toCommand() {
        return AddProductTypeCommand.builder()
                .productId(productId)
                .name(name)
                .price(price)
                .quantity(quantity)
                .build();
    }
}
