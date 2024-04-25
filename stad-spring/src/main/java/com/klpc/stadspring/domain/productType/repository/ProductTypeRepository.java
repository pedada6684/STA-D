package com.klpc.stadspring.domain.productType.repository;

import com.klpc.stadspring.domain.productType.entity.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductTypeRepository extends JpaRepository<ProductType, Long> {
    @Query("SELECT t FROM ProductType t Where t.product.id = :id")
    Optional<List<ProductType>> getProductTypeList(@Param("id") Long id);

}
