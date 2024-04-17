package com.klpc.stadspring.domain.productOrder.repository;

import com.klpc.stadspring.domain.productOrder.entity.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOrderRepository extends JpaRepository<ProductOrder,Long> {
}
