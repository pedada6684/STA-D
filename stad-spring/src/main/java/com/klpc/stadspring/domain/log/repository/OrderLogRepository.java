package com.klpc.stadspring.domain.log.repository;

import com.klpc.stadspring.domain.log.entity.OrderLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderLogRepository extends JpaRepository<OrderLog, Long> {
}
