package com.klpc.stadspring.domain.productType.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.klpc.stadspring.domain.cart.entity.CartProduct;
import com.klpc.stadspring.domain.option.entity.ProductOption;
import com.klpc.stadspring.domain.orderProduct.entity.OrderProduct;
import com.klpc.stadspring.domain.product.entity.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductType {

    @Id
    @GeneratedValue
    @Column(name = "product_type_id")
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToMany(mappedBy = "productType", cascade = CascadeType.REMOVE,  orphanRemoval = true)
    private List<ProductOption> productOptions;

    @OneToMany(mappedBy = "productType", cascade = CascadeType.REMOVE,  orphanRemoval = true)
    @JsonIgnore
    private List<CartProduct> cartProduct;

    @OneToMany(mappedBy = "productType")
    private List<OrderProduct> orderProduct;

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

    public void modifyQuantity(Long cnt){
        this.quantity += cnt;
    }
}
