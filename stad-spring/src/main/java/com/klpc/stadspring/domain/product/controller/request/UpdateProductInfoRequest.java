package com.klpc.stadspring.domain.product.controller.request;

import com.klpc.stadspring.domain.product.service.command.UpdateProductInfoCommand;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateProductInfoRequest {
    private Long id;
    private String name;
    private Long price;
    private Long quantity;
    private String introduction;
    private String thumbnail;
    private String category;

    public UpdateProductInfoCommand toCommand(){
        return UpdateProductInfoCommand.builder()
                .id(id)
                .name(name)
                .price(price)
                .quantity(quantity)
                .introduction(introduction)
                .thumbnail(thumbnail)
                .category(category)
                .build();
    }
}
