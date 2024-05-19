package com.klpc.stadstats.domain.log.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdvertStatistics {
    @Id
    @Column(name = "advert_statistics_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "advert_id")
    private Long advertId;

    @Column(name = "advert_video_count")
    private Long advertVideoCount;

    @Column(name = "advert_click_count")
    private Long advertClickCount;

    @Column(name = "order_count")
    private Long orderCount;

    @Column(name = "order_cancel_count")
    private Long orderCancelCount;

    @Column(name = "revenue")
    private Long revenue;

    @Column(name = "date")
    private LocalDate date;

    public static AdvertStatistics createNewAdvertStatistics(
        Long advertId,
        Long advertVideoCount,
        Long advertClickCount,
        Long orderCount,
        Long orderCancelCount,
        Long revenue
    ){
        AdvertStatistics advertStatistics = new AdvertStatistics();
        advertStatistics.advertId = advertId;
        advertStatistics.advertVideoCount = advertVideoCount;
        advertStatistics.advertClickCount = advertClickCount;
        advertStatistics.orderCount = orderCount;
        advertStatistics.orderCancelCount= orderCancelCount;
        advertStatistics.revenue = revenue;
        advertStatistics.date = LocalDate.now();
        return advertStatistics;
    }

    public void updateCounts(Long newClicks,
                             Long newVideos,
                             Long newOrders,
                             Long newOrderCancels,
                             Long newRevenue) {
        if (newClicks != null) {
            this.advertClickCount += newClicks;
        }
        if (newVideos != null) {
            this.advertVideoCount += newVideos;
        }
        if (newOrders != null) {
            this.orderCount += newOrders;
        }
        if (newOrderCancels != null) {
            this.orderCancelCount += newOrderCancels;
        }
        if (newRevenue != null) {
            this.revenue += newRevenue;
        }
    }
}
