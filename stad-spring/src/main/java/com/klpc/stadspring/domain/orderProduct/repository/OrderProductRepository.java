package com.klpc.stadspring.domain.orderProduct.repository;

import com.klpc.stadspring.domain.orderProduct.entity.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
}
