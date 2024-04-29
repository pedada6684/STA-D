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
    private String thumbnail;
    private Long cityDeliveryFee;
    private Long mtDeliveryFee;
    private String expStart;
    private String expEnd;

    public UpdateProductInfoCommand toCommand(){
        return UpdateProductInfoCommand.builder()
                .id(id)
                .thumbnail(thumbnail)
                .cityDeliveryFee(cityDeliveryFee)
                .mtDeliveryFee(mtDeliveryFee)
                .expStart(expStart)
                .expEnd(expEnd)
                .build();
    }
}
