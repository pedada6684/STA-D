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
public class OrderReturnLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_return_log_id")
    private Long id;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "advert_id")
    private Long advertId;

    @Column(name = "reg_date")
    private LocalDateTime regDate;

    public static OrderReturnLog createNewOrderReturnLog(
            Long orderId,
            Long userId,
            Long advertId,
            LocalDateTime regDate
    ) {
        OrderReturnLog orderReturnLog = new OrderReturnLog();
        orderReturnLog.orderId = orderId;
        orderReturnLog.userId = userId;
        orderReturnLog.advertId = advertId;
        orderReturnLog.regDate = regDate;
        return orderReturnLog;
    }
}
