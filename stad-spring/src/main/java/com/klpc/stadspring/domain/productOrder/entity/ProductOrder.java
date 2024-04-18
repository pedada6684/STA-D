package com.klpc.stadspring.domain.productOrder.entity;

import com.klpc.stadspring.domain.delivery.entity.Delivery;
import com.klpc.stadspring.domain.product.entity.Product;
import com.klpc.stadspring.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductOrder {

    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime orderDate;

    private Long cnt;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private Long contentId;

    private Long advertId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "productOrder")
    private List<Product> products;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "productOrder")
    private Delivery delivery;

}
