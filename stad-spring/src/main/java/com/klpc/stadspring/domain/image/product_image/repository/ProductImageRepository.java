package com.klpc.stadspring.domain.image.product_image.repository;

import com.klpc.stadspring.domain.image.product_image.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

}
