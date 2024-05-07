package com.klpc.stadspring.domain.product.controller.request;

import com.klpc.stadspring.domain.product.service.command.AddProductCommand;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddProductRequest {

    /**
     *  상품 등록 요청
     */
    private Long advertId;
    private String name;
    private List<String> imgs;
    private String thumbnail;
    private Long cityDeliveryFee;
    private Long mtDeliveryFee;
    private LocalDateTime expStart;
    private LocalDateTime expEnd;
    private List<AddProductRequestProductType> productTypeList;

}
