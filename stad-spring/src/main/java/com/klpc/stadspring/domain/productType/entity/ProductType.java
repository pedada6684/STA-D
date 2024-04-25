package com.klpc.stadspring.domain.productType.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.klpc.stadspring.domain.product.entity.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_id")
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Long price;

    @Column(name = "quantity")
    private Long quantity;

    public static ProductType createNewProductType(
            Product product,
            String name,
            Long price,
            Long quantity
    ) {
        ProductType productType = new ProductType();
        productType.product = product;
        productType.name = name;
        productType.price = price;
        productType.quantity = quantity;
        return productType;
    }
}