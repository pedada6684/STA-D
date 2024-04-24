package com.klpc.stadspring.domain.orders.repository;

import com.klpc.stadspring.domain.orders.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders,Long> {
}
