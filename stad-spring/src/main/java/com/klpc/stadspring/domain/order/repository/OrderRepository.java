package com.klpc.stadspring.domain.order.repository;

import com.klpc.stadspring.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
