package com.klpc.stadspring.domain.log.repository;

import com.klpc.stadspring.domain.log.entity.OrderReturnLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderReturnLogRepository extends JpaRepository<OrderReturnLog, Long> {
}
