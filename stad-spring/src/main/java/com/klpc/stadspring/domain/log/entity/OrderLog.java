package com.klpc.stadspring.domain.log.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_log_id")
    private Long id;

    @Column(name = "advert_video_id")
    private Long advertVideoId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "content_id")
    private Long contentId;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "reg_date")
    private LocalDateTime regDate;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    public static OrderLog createNewOrderLog(
        Long userId,
        Long contentId,
        Long productId,
        Boolean status,
        LocalDateTime regDate,
        LocalDateTime updateDate
    ) {
        OrderLog orderLog = new OrderLog();
        orderLog.userId = userId;
        orderLog.contentId = contentId;
        orderLog.productId = productId;
        orderLog.status = status;
        orderLog.regDate = regDate;
        orderLog.updateDate = updateDate;
        return orderLog;
    }
}