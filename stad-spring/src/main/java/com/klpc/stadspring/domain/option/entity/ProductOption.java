package com.klpc.stadspring.domain.option.entity;

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
public class ProductOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_id")
    Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;

    @Column(name = "name")
    String name;

    @Column(name = "value")
    String value;

    public static ProductOption createNewOption(
            String name,
            String value
    ) {
        ProductOption productOption = new ProductOption();
        productOption.name = name;
        productOption.value = value;
        return productOption;
    }

}
