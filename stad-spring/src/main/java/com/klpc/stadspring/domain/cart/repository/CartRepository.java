package com.klpc.stadspring.domain.cart.repository;

import com.klpc.stadspring.domain.cart.entity.Cart;
import com.klpc.stadspring.domain.cart.entity.CartProduct;
import com.klpc.stadspring.domain.product_review.entity.ProductReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,Long> {
    @Query("SELECT c FROM Cart c Where c.user.id =:userId")
    Optional<Cart> findByUserId(Long userId);

    // 장바구니 상품 리스트 조회
    @Query("SELECT c FROM CartProduct c Where c.cart.id = :cartId")
    Optional<List<CartProduct>> getCartProductByCartId(@Param("cartId") Long cartId);
}
