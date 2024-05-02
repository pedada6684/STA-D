package com.klpc.stadspring.domain.product.controller.request;

import com.klpc.stadspring.domain.product.service.command.AddProductCommand;
import com.klpc.stadspring.domain.product.service.command.UpdateProductInfoCommand;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductPostRequest {

    /**
     *  상품 등록 요청
     */
    private Long userId;
    private Long advertId;
    private String name;
    private List<MultipartFile> images;
    private MultipartFile thumbnail;
    private Long cityDeliveryFee;
    private Long mtDeliveryFee;
    private LocalDateTime expStart;
    private LocalDateTime expEnd;

    public AddProductCommand toCommand(){
        return AddProductCommand.builder()
                .userId(userId)
                .advertId(advertId)
                .name(name)
                .images(images)
                .thumbnail(thumbnail)
                .cityDeliveryFee(cityDeliveryFee)
                .mtDeliveryFee(mtDeliveryFee)
                .expStart(expStart)
                .expEnd(expEnd)
                .build();
    }
}
