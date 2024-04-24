package com.klpc.stadspring.domain.delivery.entity;

import com.klpc.stadspring.domain.orders.entity.Orders;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;  //ING, DONE, BACK, CANCEL

    @Column(length = 20)
    private String phone_number;

    @Column(length = 20)
    private String name;

    @Column(length = 3000)
    private String location;

    @OneToOne
    @JoinColumn(name = "delivery_id")
    private Orders orders;
}
