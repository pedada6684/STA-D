package com.klpc.stadspring.domain.log.repository;

import com.klpc.stadspring.domain.log.entity.OrderReturnLog;
import com.klpc.stadspring.domain.log.entity.OrderUserInfoLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderUserInfoLogRepository extends JpaRepository<OrderUserInfoLog, Long> {
}
