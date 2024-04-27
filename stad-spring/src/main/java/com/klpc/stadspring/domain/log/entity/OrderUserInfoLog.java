package com.klpc.stadspring.domain.log.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.joda.time.LocalDate;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderUserInfoLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_user_info_log")
    private Long id;

    @Column(name = "advert_id")
    private Long advertId;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "userId")
    private Long userId;

    @Column(name = "reg_date")
    private LocalDate regDate;

}