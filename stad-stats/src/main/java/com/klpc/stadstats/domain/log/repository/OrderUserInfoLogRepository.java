package com.klpc.stadstats.domain.log.repository;

import com.klpc.stadstats.domain.log.entity.OrderUserInfoLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface OrderUserInfoLogRepository extends JpaRepository<OrderUserInfoLog, Long> {
}
