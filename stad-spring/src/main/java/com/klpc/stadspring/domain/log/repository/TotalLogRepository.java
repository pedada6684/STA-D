package com.klpc.stadspring.domain.log.repository;


import com.klpc.stadspring.domain.log.entity.AdvertStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface TotalLogRepository extends JpaRepository<AdvertStatistics, Long>{
    @Query("""
            SELECT SUM(a.advertClickCount) as totalClicks,
            SUM(a.advertVideoCount) as totalVideos,
            SUM(a.orderCount) as totalOrders,
            SUM(a.revenue) as totalRevenue
            FROM AdvertStatistics a
            WHERE a.advertId = :advertId AND a.date>= :startDate
            """)
    Optional<Object[]> getTotalLog(@Param("advertId") Long advertId, @Param("startDate") LocalDateTime startDate);
}
