package com.klpc.stadspring.domain.option.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.klpc.stadspring.domain.product.entity.Product;
import com.klpc.stadspring.domain.productType.entity.ProductType;
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

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_type_id")
    ProductType productType;

    @Column(name = "name")
    String name;

    @Column(name = "value")
    Long value;

    public static ProductOption createNewOption(
            ProductType productType,
            String name,
            Long value
    ) {
        ProductOption productOption = new ProductOption();
        productOption.productType = productType;
        productOption.name = name;
        productOption.value = value;
        return productOption;
    }

    public void modifyOption(
            String name,
            Long value
    ){
        this.name=name;
        this.value=value;
    }
}
