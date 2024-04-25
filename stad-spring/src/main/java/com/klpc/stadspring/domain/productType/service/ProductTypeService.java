package com.klpc.stadspring.domain.productType.service;

import com.klpc.stadspring.domain.product.entity.Product;
import com.klpc.stadspring.domain.product.repository.ProductRepository;
import com.klpc.stadspring.domain.productType.entity.ProductType;
import com.klpc.stadspring.domain.productType.repository.ProductTypeRepository;
import com.klpc.stadspring.domain.productType.service.command.AddProductTypeCommand;
import com.klpc.stadspring.domain.productType.service.command.DeleteProductTypeCommand;
import com.klpc.stadspring.global.response.ErrorCode;
import com.klpc.stadspring.global.response.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

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

    public List<ProductType> getProductTypeList(Long productId) {

        List<ProductType> productTypeList = productTypeRepository.getProductTypeList(productId)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
        return productTypeList;
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

    public void deleteProductType(DeleteProductTypeCommand command) {
        log.info("DeleteOptionCommand: "+command);
        ProductType productType = productTypeRepository.findById(command.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
        productTypeRepository.delete(productType);
    }
}
