package com.klpc.stadspring.domain.product.repository;

import com.klpc.stadspring.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {
}