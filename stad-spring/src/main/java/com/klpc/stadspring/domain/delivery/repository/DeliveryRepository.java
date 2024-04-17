package com.klpc.stadspring.domain.delivery.repository;

import com.klpc.stadspring.domain.delivery.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery,Long> {
}
