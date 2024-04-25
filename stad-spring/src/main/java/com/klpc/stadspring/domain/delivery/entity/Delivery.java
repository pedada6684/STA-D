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
    private DeliveryStatus status;  //ING, DONE, REFUND, CANCEL

    @Column(length = 20)
    private String phone_number;

    @Column(length = 20)
    private String name;

    private String location;

    @Column(length = 3000)
    private String locationDetail;

    private String locationNum;

    private String locationName;

    @OneToOne
    @JoinColumn(name = "delivery_id")
    private Orders orders;

    public static Delivery createToDelivery(
            String phone_number,
            String name,
            String location,
            String locationDetail,
            String locationName,
            String locationNum
    ){
        Delivery delivery = new Delivery();
        delivery.name=name;
        delivery.phone_number=phone_number;
        delivery.location=location;
        delivery.locationDetail=locationDetail;
        delivery.locationName=locationName;
        delivery.locationNum=locationNum;
        delivery.status=DeliveryStatus.ING;
        return delivery;
    }

    public void linkedOrders(Orders orders){
        this.orders=orders;
    }
}
