package com.klpc.stadspring.domain.product.service.command;

import com.klpc.stadspring.domain.image.product_image.entity.ProductImage;
import com.klpc.stadspring.domain.product.controller.response.GetProductInfoResponse;
import com.klpc.stadspring.domain.product.entity.Product;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UpdateProductInfoCommand {
    /**
     *  상품 정보 수정
     */

    private Long id;
    private String name;
    private Long price;
    private Long quantity;
    private String introduction;
    private String thumbnail;
    private String category;
    private List<ProductImage> images ;
    private String sellStart;
    private String sellEnd;
    private Long cityDeliveryFee;
    private Long mtDeliveryFee;
    private String expStart;
    private String expEnd;
    private String deliveryDate;


//    public static GetProductInfoResponse ConvertProductInfoCommand(Product product){
//        return GetProductInfoResponse.builder()
//                .id(product.getId())
//                .name(product.getName())
//                .price(product.getPrice())
//                .quantity(product.getQuantity())
//                .introduction(product.getIntroduction())
//                .thumbnail(product.getThumbnail())
//                .category(product.getCategory())
//                .build();
//    }
}
