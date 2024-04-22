package com.klpc.stadspring.domain.product.controller.request;

import com.klpc.stadspring.domain.product.service.command.AddProductCommand;
import com.klpc.stadspring.domain.product.service.command.UpdateProductInfoCommand;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductPostRequest {

    /**
     *  상품 등록 요청
     */

    String name;
    Long price;
    Long quantity;
    MultipartFile introduction;
    MultipartFile thumbnail;
    String category;
    LocalDateTime sellStart;
    LocalDateTime sellEnd;
    Long cityDeliveryFee;
    Long mtDeliveryFee;
    LocalDateTime expStart;
    LocalDateTime expEnd;
    LocalDateTime deliveryDate;

    public AddProductCommand toCommand(){
        return AddProductCommand.builder()
                .name(name)
                .price(price)
                .introduction(introduction)
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
