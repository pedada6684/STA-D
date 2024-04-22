package com.klpc.stadspring.domain.product.service;

import com.klpc.stadspring.domain.product.controller.response.GetProductListByAdverseResponse;
import com.klpc.stadspring.domain.product.entity.Product;
import com.klpc.stadspring.domain.product.service.command.AddProductCommand;
import com.klpc.stadspring.domain.product.service.command.DeleteProductCommand;
import com.klpc.stadspring.domain.product.service.command.GetProductListByAdverseCommand;
import com.klpc.stadspring.domain.product.service.command.UpdateProductInfoCommand;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    // 광고에 포함된 상품 리스트
    List<Product> getProductListByAdverseId(Long adverseId);

    // 상품 상세 페이지
    Product getProductInfo(Long id);

    // 상품 등록
    Product addProduct(AddProductCommand addProductCommand);

    void deleteProduct(DeleteProductCommand deleteProductCommand);

    Product updateProductInfo(UpdateProductInfoCommand updateProductInfoCommand);

    // 상품 옵션 리스트
}
