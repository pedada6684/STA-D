package com.klpc.stadspring.domain.log.repository;

import com.klpc.stadspring.domain.log.entity.OrderLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OrderLogRepository extends JpaRepository<OrderLog, Long> {
    @Query("SELECT COUNT(o) FROM OrderLog o WHERE o.advertVideoId = :advertVideoId AND o.status = TRUE")
    Optional<Long> countOrderLog(@Param("advertVideoId") Long advertVideoId);

    @Query("SELECT COUNT(o) FROM OrderLog o WHERE o.advertVideoId = :advertVideoId AND o.status = FALSE")
    Optional<Long> countOrderCancelLog(@Param("advertVideoId") Long advertVideoId);
}
