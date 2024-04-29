package com.klpc.stadspring.domain.product.service.command;

import com.klpc.stadspring.domain.product.controller.response.GetProductInfoResponse;
import com.klpc.stadspring.domain.product.entity.Product;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class AddProductCommand {
    /**
     *  상품 등록
     */
    private Long userId;
    private Long advertId;
    private List<MultipartFile> images;
    private MultipartFile thumbnail;
    private Long cityDeliveryFee;
    private Long mtDeliveryFee;
    private LocalDateTime expStart;
    private LocalDateTime expEnd;

//    public static AddProductCommand ConvertAddProductImageCommand(Product product){
//        return AddProductCommand.builder()
//                .name(product.getName())
//                .build();
//    }
}
