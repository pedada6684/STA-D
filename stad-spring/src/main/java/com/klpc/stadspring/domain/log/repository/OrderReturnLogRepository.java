package com.klpc.stadspring.domain.log.repository;

import com.klpc.stadspring.domain.log.entity.OrderReturnLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OrderReturnLogRepository extends JpaRepository<OrderReturnLog, Long> {
    @Query("SELECT COUNT(o) FROM OrderReturnLog o WHERE o.advertId = :advertId")
    Optional<Long> countOrderReturnLog(@Param("advertId") Long advertId);
}
