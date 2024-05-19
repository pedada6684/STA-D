package com.klpc.stadspring.domain.productType.service.command;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.klpc.stadspring.domain.cart.entity.CartProduct;
import com.klpc.stadspring.domain.option.entity.ProductOption;
import com.klpc.stadspring.domain.orderProduct.entity.OrderProduct;
import com.klpc.stadspring.domain.product.entity.Product;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProductTypeInfoCommand {
    private Long id;
    private List<ProductOption> productOptions;
    private String name;
    private Long price;
    private Long quantity;
    private String thumbnail;
}
