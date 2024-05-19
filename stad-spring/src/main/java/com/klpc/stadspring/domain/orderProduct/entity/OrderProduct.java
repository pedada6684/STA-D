package com.klpc.stadspring.domain.orderProduct.entity;

import com.klpc.stadspring.domain.product.entity.Product;
import com.klpc.stadspring.domain.orders.entity.Orders;
import com.klpc.stadspring.domain.productType.entity.ProductType;
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

    private Long optionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orders_id")
    private Orders orders;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_type_id")
    private ProductType productType;

    public static OrderProduct createToOrderProduct(Long cnt, Long optionId){
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.cnt=cnt;
        orderProduct.optionId=optionId;
        return orderProduct;
    }

    public void linkedOrders(Orders orders) {
        this.orders = orders;
    }

    public void linkedProductType(ProductType productType){
        this.productType=productType;
    }

}
