package com.klpc.stadspring.domain.cart.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.klpc.stadspring.domain.product.entity.Product;
import com.klpc.stadspring.domain.productType.entity.ProductType;
import com.klpc.stadspring.domain.product_review.entity.ProductReview;
import com.klpc.stadspring.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_product_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_type_id")
    private ProductType productType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Setter
    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "advert_id")
    private Long advertId;

    @Column(name = "content_id")
    private Long contentId;

    @Column(name = "option_id")
    private Long optionId;

    public static CartProduct createNewCartProduct(
            ProductType productType,
            User user,
            Long quantity,
            Long advertId,
            Long contentId,
            Long optionId
    ) {
        CartProduct cartProduct = new CartProduct();
        cartProduct.productType = productType;
        cartProduct.user= user;
        cartProduct.quantity = quantity;
        cartProduct.advertId = advertId;
        cartProduct.contentId = contentId;
        cartProduct.optionId = optionId;
        return cartProduct;
    }
}
