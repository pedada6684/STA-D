package com.klpc.stadspring.domain.product.controller.request;

import com.klpc.stadspring.domain.image.product_image.entity.ProductImage;
import com.klpc.stadspring.domain.product.service.command.UpdateProductInfoCommand;
import lombok.*;

import java.util.List;

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
    private String thumbnail;
    private String category;
    private String sellStart;
    private String sellEnd;
    private Long cityDeliveryFee;
    private Long mtDeliveryFee;
    private String expStart;
    private String expEnd;
    private String deliveryDate;

    public UpdateProductInfoCommand toCommand(){
        return UpdateProductInfoCommand.builder()
                .id(id)
                .name(name)
                .price(price)
                .quantity(quantity)
                .thumbnail(thumbnail)
                .category(category)
                .sellStart(sellStart)
                .sellEnd(sellEnd)
                .cityDeliveryFee(cityDeliveryFee)
                .mtDeliveryFee(mtDeliveryFee)
                .expStart(expStart)
                .expEnd(expEnd)
                .deliveryDate(deliveryDate)
                .build();
    }
}
