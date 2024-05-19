package com.klpc.stadspring.domain.image.product_image.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.klpc.stadspring.domain.product.entity.Product;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_image_id")
    Long id;

    @Column(name = "img", length = 3000)
    String img;

    @JsonIgnore
    @JoinColumn(name = "product_id")
    @ManyToOne(fetch = FetchType.LAZY)
    Product product;

    public static ProductImage createNewProductImage(
            String img,
            Product product
    ) {
        ProductImage productImage = new ProductImage();
        productImage.img = img;
        productImage.product = product;
        return productImage;
    }
}