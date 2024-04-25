package com.klpc.stadspring.domain.orderProduct.entity;

import com.klpc.stadspring.domain.product.entity.Product;
import com.klpc.stadspring.domain.orders.entity.Orders;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.core.annotation.Order;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long cnt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orders_id")
    private Orders orders;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    public static OrderProduct createToOrderProduct(Long cnt){
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.cnt=cnt;
        return orderProduct;
    }

    public void linkedOrders(Orders orders) {
        this.orders = orders;
    }

    public void linkedProduct(Product product){
        this.product=product;
    }

}
