package com.klpc.stadspring.domain.cart.entity;

import com.klpc.stadspring.domain.product.entity.Product;
import com.klpc.stadspring.domain.product_review.entity.ProductReview;
import com.klpc.stadspring.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_product_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Setter
    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "advert_id")
    private Long advertId;

    @Column(name = "content_id")
    private Long contentId;

    public static CartProduct createNewCartProduct(
            Cart cart,
            Product product,
            Long quantity,
            Long advertId,
            Long contentId
    ) {
        CartProduct cartProduct = new CartProduct();
        cartProduct.cart = cart;
        cartProduct.product = product;
        cartProduct.quantity = quantity;
        cartProduct.advertId = advertId;
        cartProduct.contentId = contentId;
        return cartProduct;
    }
}
