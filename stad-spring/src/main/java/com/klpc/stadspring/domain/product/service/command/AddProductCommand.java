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
    String name;
    Long price;
    Long quantity;
    List<MultipartFile> images;
    MultipartFile thumbnail;
    String category;
    LocalDateTime sellStart;
    LocalDateTime sellEnd;
    Long cityDeliveryFee;
    Long mtDeliveryFee;
    LocalDateTime expStart;
    LocalDateTime expEnd;
    LocalDateTime deliveryDate;

//    public static AddProductCommand ConvertAddProductImageCommand(Product product){
//        return AddProductCommand.builder()
//                .name(product.getName())
//                .build();
//    }
}
