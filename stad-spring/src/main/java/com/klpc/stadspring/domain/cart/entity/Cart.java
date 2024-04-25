package com.klpc.stadspring.domain.cart.entity;

import com.klpc.stadspring.domain.productType.entity.ProductType;
import com.klpc.stadspring.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;

    @Setter
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.REMOVE,  orphanRemoval = true)
    private List<CartProduct> cartProduct;
}
