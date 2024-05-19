package com.klpc.stadspring.domain.product.repository;
import com.klpc.stadspring.domain.product.entity.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.List;

public interface ProductRepositoryCustom {
    // 광고에 포함된 상품 리스트
    @Query("SELECT p FROM Product p Where p.advert.id = :advertId AND p.status = true")
    Optional<List<Product>> getProductListByAdvertId(@Param("advertId") Long advertId);

    // 상품 상세 페이지
    @Query("SELECT p FROM Product p Where id = :id AND p.status = true")
    Optional<Product> getProductInfo(@Param("id") Long id);

}
