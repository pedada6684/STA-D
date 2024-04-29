package com.klpc.stadspring.domain.log.repository;


import com.klpc.stadspring.domain.log.entity.AdvertStatistics;
import com.klpc.stadspring.domain.log.service.command.GetDailyCountCommand;
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
            SUM(a.revenue) as totalRevenue
            FROM AdvertStatistics a
            WHERE a.advertId = :advertId AND a.date>= :startDate
            """)
    Optional<Object[]> getTotalLog(@Param("advertId") Long advertId, @Param("startDate") LocalDateTime startDate);

    /**
     * 30일간 클릭 숫자 통계 구하기
     * @param startDate
     * @return
     */
    @Query("""
            SELECT a.date, SUM(a.advertClickCount) as clickTotal
            FROM AdvertStatistics a WHERE a.date >= :startDate
            GROUP BY a.date ORDER BY a.date ASC
            """)
    Optional<List<GetDailyCountCommand>> getDailyAdvertClickCount(LocalDate startDate);

    /**
     * 30일간 비디오 숫자 통계 구하기
     * @param startDate
     * @return
     */
    @Query("""
            SELECT a.date, SUM(a.advertVideoCount) as videoTotal
            FROM AdvertStatistics a WHERE a.date >= :startDate
            GROUP BY a.date ORDER BY a.date ASC
            """)
    Optional<List<GetDailyCountCommand>> getDailyAdvertVideoCount(LocalDate startDate);

    /**
     * 30일간 주문 숫자 통계 구하기
     * @param startDate
     * @return
     */
    @Query("""
            SELECT a.date, SUM(a.orderCount) as orderCount
            FROM AdvertStatistics a WHERE a.date >= :startDate
            GROUP BY a.date ORDER BY a.date ASC
            """)
    Optional<List<GetDailyCountCommand>> getDailyOrderCount(LocalDate startDate);

    /**
     * 30일간 수익 통계 구하기
     * @param startDate
     * @return
     */
    @Query("""
            SELECT a.date, SUM(a.revenue) as revenue
            FROM AdvertStatistics a WHERE a.date >= :startDate
            GROUP BY a.date ORDER BY a.date ASC
            """)
    Optional<List<GetDailyCountCommand>> getDailyRevenue(LocalDate startDate);
}
