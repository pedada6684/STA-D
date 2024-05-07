package com.klpc.stadspring.domain.productType.service;

import com.klpc.stadspring.domain.option.entity.ProductOption;
import com.klpc.stadspring.domain.product.entity.Product;
import com.klpc.stadspring.domain.product.repository.ProductRepository;
import com.klpc.stadspring.domain.productType.controller.response.GetProductTypeListByUserIdResponse;
import com.klpc.stadspring.domain.productType.controller.response.GetProductTypeListResponse;
import com.klpc.stadspring.domain.productType.entity.ProductType;
import com.klpc.stadspring.domain.productType.repository.ProductTypeRepository;
import com.klpc.stadspring.domain.productType.service.command.AddProductTypeCommand;
import com.klpc.stadspring.domain.productType.service.command.DeleteProductTypeCommand;
import com.klpc.stadspring.domain.productType.service.command.GetProductTypeListByUserIdCommand;
import com.klpc.stadspring.domain.productType.service.command.ProductTypeInfoCommand;
import com.klpc.stadspring.domain.product_review.controller.response.GetProductReviewListResponse;
import com.klpc.stadspring.domain.product_review.entity.ProductReview;
import com.klpc.stadspring.domain.product_review.service.command.ProductReviewInfoCommand;
import com.klpc.stadspring.domain.user.entity.User;
import com.klpc.stadspring.domain.user.repository.UserRepository;
import com.klpc.stadspring.global.response.ErrorCode;
import com.klpc.stadspring.global.response.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
@Service
public class ProductTypeService {

    private final ProductTypeRepository productTypeRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
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

        productType.deleteProductType(productType);

        productTypeRepository.save(productType);
    }

    public GetProductTypeListByUserIdResponse getProductTypeListByUserId(Long userId){
        userRepository.findById(userId).orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));

        List<GetProductTypeListByUserIdCommand> commands = productTypeRepository.getProductTypeByUserId(userId).orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));

        return GetProductTypeListByUserIdResponse.builder().data(commands).build();
    }
}
