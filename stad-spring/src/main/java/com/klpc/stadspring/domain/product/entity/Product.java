package com.klpc.stadspring.domain.product.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.klpc.stadspring.domain.advert.entity.Advert;
import com.klpc.stadspring.domain.image.product_image.entity.ProductImage;
import com.klpc.stadspring.domain.orderProduct.entity.OrderProduct;
import com.klpc.stadspring.domain.product.service.command.UpdateProductInfoCommand;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    // 추후 제거하고 DTO 설정으로 교체할것
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "advert_id")
    private Advert advert;

    @OneToMany(mappedBy = "product")
    private List<OrderProduct> orderProduct;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Long price;

    @Column(name = "quantity")
    private Long quantity;

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE,  orphanRemoval = true)
    private List<ProductImage> images;

    @Column(name = "thumbnail")
    private String thumbnail;

    @Column(name = "city_delivery_fee")
    private Long cityDeliveryFee;

    @Column(name = "mt_delivery_fee")
    private Long mtDeliveryFee;

    @Column(name = "exp_start")
    private LocalDateTime expStart;

    @Column(name = "exp_end")
    private LocalDateTime expEnd;

    public static Product createNewProduct(
            Advert advert,
            String name,
            Long price,
            Long quantity,
            String thumbnail,
            Long cityDeliveryFee,
            Long mtDeliveryFee,
            LocalDateTime expStart,
            LocalDateTime expEnd
    ) {
        Product product = new Product();
        product.advert= advert;
        product.name = name;              // 상품명 설정
        product.price = price;            // 가격 설정
        product.quantity = quantity;      // 수량 설정
        product.thumbnail = thumbnail;    // 썸네일 이미지 경로 설정
        product.cityDeliveryFee = cityDeliveryFee;
        product.mtDeliveryFee = mtDeliveryFee;
        product.expStart = expStart;
        product.expEnd = expEnd;
        return product;
    }

    public void update(UpdateProductInfoCommand command) {
        if (command.getName() != null) {
            this.name = command.getName();
        }
        if (command.getPrice() != null) {
            this.price = command.getPrice();
        }
        if (command.getQuantity() != null) {
            this.quantity = command.getQuantity();
        }
        if (command.getThumbnail() != null) {
            this.thumbnail = command.getThumbnail();
        }
    }

    public void updateProductThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void linkAdvert(Advert advert){
        this.advert=advert;
    }
}


