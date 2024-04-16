package com.klpc.stadspring.domain.cart.repository;

import com.klpc.stadspring.domain.cart.entity.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartProductRepository extends JpaRepository<CartProduct,Long> {
}
