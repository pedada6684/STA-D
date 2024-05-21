package com.klpc.stadstats.domain.log.repository;

import com.klpc.stadstats.domain.log.entity.OrderLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

public interface OrderLogRepository extends JpaRepository<OrderLog, Long> {
    @Query("SELECT COUNT(o) FROM OrderLog o WHERE o.advertId = :advertId AND o.status = TRUE AND o.regDate >= :oneHourAgo")
    Optional<Long> countOrderLog(@Param("advertId") Long advertId, @Param("oneHourAgo") LocalDateTime oneHourAgo);

    @Query("SELECT COUNT(o) FROM OrderLog o WHERE o.advertId = :advertId AND o.status = FALSE AND o.regDate >= :oneHourAgo")
    Optional<Long> countOrderCancelLog(@Param("advertId") Long advertId, @Param("oneHourAgo") LocalDateTime oneHourAgo);

    // 1시간 동안의 수익
    @Query("SELECT SUM(o.price) FROM OrderLog o WHERE o.advertId = :advertId AND o.regDate >= :oneHourAgo")
    Optional<Long> sumClicksByAdvertIdAndDateRange(@Param("advertId") Long advertId, @Param("oneHourAgo") LocalDateTime oneHourAgo);

    @Query("SELECT COUNT(o) FROM OrderLog o WHERE o.advertId = :advertId AND o.status = TRUE")
    Long findByAdvertId(@Param("advertId") Long advertId);

    @Query("SELECT COUNT(o) FROM OrderLog o WHERE o.advertId = :advertId AND o.status = FALSE")
    Long findByAdvertIdCancel(@Param("advertId") Long advertId);

    @Query("SELECT SUM(o.price) FROM OrderLog o WHERE o.advertId = :advertId")
    Long findByAdvertIdRevenue(@Param("advertId") Long advertId);
}
