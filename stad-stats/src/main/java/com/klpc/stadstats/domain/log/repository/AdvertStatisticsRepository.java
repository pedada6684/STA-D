package com.klpc.stadstats.domain.log.repository;


import com.klpc.stadstats.domain.log.entity.AdvertStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

public interface AdvertStatisticsRepository extends JpaRepository<AdvertStatistics, Long>{
    @Query("""
            SELECT SUM(a.advertClickCount) as totalClicks,
            SUM(a.advertVideoCount) as totalVideos,
            SUM(a.orderCount) as totalOrders,
            SUM(a.orderCancelCount) as totalCancelCounts,
            SUM(a.revenue) as totalRevenue
            FROM AdvertStatistics a
            WHERE a.advertId = :advertId AND a.date >= :startDate
            """)
    Optional<Object[]> getTotalLog(@Param("advertId") Long advertId, @Param("startDate") LocalDate startDate);

    /**
     * 30일간 클릭 숫자 통계 구하기
     */
    @Query("""
        SELECT a.date, SUM(a.advertClickCount) as value
        FROM AdvertStatistics a
        WHERE a.date >= :startDate AND a.advertId = :advertId
        GROUP BY a.date
        ORDER BY a.date ASC
        """)
    Optional<List<Object[]>> getDailyAdvertClickCount(@Param("advertId") Long advertId, @Param("startDate") LocalDate startDate);

    /**
     * 30일간 비디오 숫자 통계 구하기
     */
    @Query("""
            SELECT a.date as date, SUM(a.advertVideoCount) as value
            FROM AdvertStatistics a
            WHERE a.date >= :startDate AND a.advertId = :advertId
            GROUP BY a.date
            ORDER BY a.date ASC
            """)
    Optional<List<Object[]>> getDailyAdvertVideoCount(@Param("advertId") Long advertId, @Param("startDate") LocalDate startDate);

    /**
     * 30일간 주문 숫자 통계 구하기
     */
    @Query("""
            SELECT a.date as date, SUM(a.orderCount) as value
            FROM AdvertStatistics a
            WHERE a.date >= :startDate AND a.advertId = :advertId
            GROUP BY a.date
            ORDER BY a.date ASC
            """)
    Optional<List<Object[]>> getDailyOrderCount(@Param("advertId") Long advertId, @Param("startDate") LocalDate startDate);

    /**
     * 30일간 수익 통계 구하기
     */
    @Query("""
            SELECT a.date as date, SUM(a.revenue) as value
            FROM AdvertStatistics a
            WHERE a.date >= :startDate AND a.advertId = :advertId
            GROUP BY a.date
            ORDER BY a.date ASC
            """)
    Optional<List<Object[]>> getDailyRevenue(@Param("advertId") Long advertId, @Param("startDate") LocalDate startDate);
}
