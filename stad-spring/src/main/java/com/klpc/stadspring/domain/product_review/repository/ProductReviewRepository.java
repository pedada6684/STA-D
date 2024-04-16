package com.klpc.stadspring.domain.product_review.repository;

import com.klpc.stadspring.domain.product_review.controller.response.GetProductReviewResponse;
import com.klpc.stadspring.domain.product_review.entity.ProductReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.List;

public interface ProductReviewRepository extends JpaRepository<ProductReview, Long> {
    // 리뷰 상세
    @Query("SELECT r FROM ProductReview r Where id = :id")
    Optional<GetProductReviewResponse> getProductInfo(@Param("id") Long id);

    // 상품 리뷰 리스트 조회
    @Query("SELECT r FROM ProductReview r Where r.product.id = :productId")
    Optional<List<ProductReview>> getReviewListByProductId(@Param("productId") Long productId);


}
