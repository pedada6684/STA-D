package com.klpc.stadspring.domain.orderProduct.entity;

import com.klpc.stadspring.domain.product.entity.Product;
import com.klpc.stadspring.domain.productOrder.entity.ProductOrder;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long cnt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productOrder_id")
    private ProductOrder productOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

}
