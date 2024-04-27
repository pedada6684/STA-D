package com.klpc.stadspring.domain.log.entity;

import jakarta.persistence.*;
<<<<<<< HEAD
import lombok.*;
=======
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
>>>>>>> 390e18d (feat: Log 집계 테이블 entity 추가)

import java.time.LocalDate;

@Entity
@Getter
<<<<<<< HEAD
@Setter
=======
>>>>>>> 390e18d (feat: Log 집계 테이블 entity 추가)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdvertStatistics {
    @Id
    @Column(name = "advert_statistics_id")
<<<<<<< HEAD
    @GeneratedValue(strategy = GenerationType.IDENTITY)
=======
>>>>>>> 390e18d (feat: Log 집계 테이블 entity 추가)
    private Long id;

    @Column(name = "advert_id")
    private Long advertId;

    @Column(name = "advert_video_count")
    private Long advertVideoCount;

    @Column(name = "advert_click_count")
    private Long advertClickCount;

    @Column(name = "order_count")
    private Long orderCount;

    @Column(name = "revenue")
    private Long revenue;

    @Column(name = "date")
    private LocalDate date;

    public static AdvertStatistics createNewAdvertStatistics(
        Long advertId,
        Long advertVideoCount,
        Long advertClickCount,
        Long orderCount,
        Long revenue,
        LocalDate date
    ){
        AdvertStatistics advertStatistics = new AdvertStatistics();
        advertStatistics.advertId = advertId;
        advertStatistics.advertVideoCount = advertVideoCount;
        advertStatistics.advertClickCount = advertClickCount;
        advertStatistics.orderCount = orderCount;
        advertStatistics.revenue = revenue;
        advertStatistics.date = date;
        return advertStatistics;
    }
}
