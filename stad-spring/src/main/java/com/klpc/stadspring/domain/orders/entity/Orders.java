package com.klpc.stadspring.domain.orders.entity;

import com.klpc.stadspring.domain.orderProduct.entity.OrderProduct;
import com.klpc.stadspring.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.core.annotation.Order;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "orders")
    private List<OrderProduct> orderProducts;

    public static Orders createToOrders(
            User user
    ){
        Orders orders = new Orders();
        orders.user=user;
        orders.status=OrderStatus.ORDER;
        orders.orderDate=LocalDateTime.now();
        return orders;
    }

    public void cancelOrders(){
        this.status = OrderStatus.CANCEL;
    }

}
