package com.klpc.stadspring.domain.cart.repository;

import com.klpc.stadspring.domain.cart.entity.CartProduct;
import com.klpc.stadspring.domain.productType.entity.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.List;

public interface CartProductRepository extends JpaRepository<CartProduct,Long> {
    @Query("""
    select
        p
    from CartProduct p
    join fetch p.productType
    where p.user.id = :userId""")
    Optional<List<CartProduct>> getCartProductByUserId(@Param("userId") Long userId);

}
