package com.klpc.stadspring.domain.productType.repository;

import com.klpc.stadspring.domain.productType.entity.ProductType;
import com.klpc.stadspring.domain.productType.service.command.GetProductTypeListByUserIdCommand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductTypeRepository extends JpaRepository<ProductType, Long> {
    @Query("SELECT t FROM ProductType t Where t.product.id = :id AND t.status= true")
    Optional<List<ProductType>> getProductTypeList(@Param("id") Long id);

    @Query("""
           SELECT new com.klpc.stadspring.domain.productType.service.command.GetProductTypeListByUserIdCommand(
           pt.id, pt.product.thumbnail, pt.name,
           pt.price, pt.quantity, COUNT(pt)) FROM ProductType pt
           WHERE pt.product.id IN (SELECT (SELECT ptt.id FROM ad.products ptt) FROM Advert ad WHERE ad.user.id=:userId)
           GROUP BY pt
           """)
    Optional<List<GetProductTypeListByUserIdCommand>> getProductTypeByUserId(@Param("userId") Long userId);

}
