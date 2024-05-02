package com.klpc.stadspring.domain.productType.service;

import com.klpc.stadspring.domain.option.entity.ProductOption;
import com.klpc.stadspring.domain.product.entity.Product;
import com.klpc.stadspring.domain.product.repository.ProductRepository;
import com.klpc.stadspring.domain.productType.controller.response.GetProductTypeListResponse;
import com.klpc.stadspring.domain.productType.entity.ProductType;
import com.klpc.stadspring.domain.productType.repository.ProductTypeRepository;
import com.klpc.stadspring.domain.productType.service.command.AddProductTypeCommand;
import com.klpc.stadspring.domain.productType.service.command.DeleteProductTypeCommand;
import com.klpc.stadspring.domain.productType.service.command.ProductTypeInfoCommand;
import com.klpc.stadspring.domain.product_review.controller.response.GetProductReviewListResponse;
import com.klpc.stadspring.domain.product_review.entity.ProductReview;
import com.klpc.stadspring.domain.product_review.service.command.ProductReviewInfoCommand;
import com.klpc.stadspring.global.response.ErrorCode;
import com.klpc.stadspring.global.response.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Service
public class ProductTypeService {

    private final ProductTypeRepository productTypeRepository;
    private final ProductRepository productRepository;
    /**
     * 타입 리스트 받아오기
     */

    public GetProductTypeListResponse getProductTypeList(Long productId) {

        List<ProductType> productTypeList = productTypeRepository.getProductTypeList(productId)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));


        List<ProductTypeInfoCommand> responseList = new ArrayList<>();

        for(ProductType productType : productTypeList){
            Product product = productType.getProduct();

            ProductTypeInfoCommand response = ProductTypeInfoCommand.builder()
                    .id(productType.getId())
                    .productOptions(productType.getProductOptions())
                    .name(productType.getName())
                    .price(productType.getPrice())
                    .quantity(productType.getQuantity())
                    .thumbnail(product.getThumbnail())
                    .build();

            responseList.add(response);
        }
        return GetProductTypeListResponse.from(responseList);
    }

    // 상품 등록
    public ProductType addProductType(AddProductTypeCommand command) {
        log.info("AddProductTypeCommand: "+command);

        Product product = productRepository.findById(command.getProductId())
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));

        ProductType newType = ProductType.createNewProductType(
                product,
                command.getName(),
                command.getPrice(),
                command.getQuantity()
        );

        productTypeRepository.save(newType);

        return newType;
    }

    public void deleteProductType(Long productTypeId) {
        ProductType productType = productTypeRepository.findById(productTypeId)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
        productTypeRepository.delete(productType);
    }
}
