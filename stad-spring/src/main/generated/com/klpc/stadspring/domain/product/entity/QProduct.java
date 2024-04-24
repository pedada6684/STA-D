package com.klpc.stadspring.domain.product.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProduct is a Querydsl query type for Product
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProduct extends EntityPathBase<Product> {

    private static final long serialVersionUID = 1767504157L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProduct product = new QProduct("product");

    public final com.klpc.stadspring.domain.advert.entity.QAdvert advert;

    public final StringPath category = createString("category");

    public final NumberPath<Long> cityDeliveryFee = createNumber("cityDeliveryFee", Long.class);

    public final DateTimePath<java.time.LocalDateTime> deliveryDate = createDateTime("deliveryDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> expEnd = createDateTime("expEnd", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> expStart = createDateTime("expStart", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath introduction = createString("introduction");

    public final NumberPath<Long> mtDeliveryFee = createNumber("mtDeliveryFee", Long.class);

    public final StringPath name = createString("name");

    public final ListPath<com.klpc.stadspring.domain.orderProduct.entity.OrderProduct, com.klpc.stadspring.domain.orderProduct.entity.QOrderProduct> orderProduct = this.<com.klpc.stadspring.domain.orderProduct.entity.OrderProduct, com.klpc.stadspring.domain.orderProduct.entity.QOrderProduct>createList("orderProduct", com.klpc.stadspring.domain.orderProduct.entity.OrderProduct.class, com.klpc.stadspring.domain.orderProduct.entity.QOrderProduct.class, PathInits.DIRECT2);

    public final NumberPath<Long> price = createNumber("price", Long.class);

    public final NumberPath<Long> quantity = createNumber("quantity", Long.class);

    public final DateTimePath<java.time.LocalDateTime> sellEnd = createDateTime("sellEnd", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> sellStart = createDateTime("sellStart", java.time.LocalDateTime.class);

    public final StringPath thumbnail = createString("thumbnail");

    public QProduct(String variable) {
        this(Product.class, forVariable(variable), INITS);
    }

    public QProduct(Path<? extends Product> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProduct(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProduct(PathMetadata metadata, PathInits inits) {
        this(Product.class, metadata, inits);
    }

    public QProduct(Class<? extends Product> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.advert = inits.isInitialized("advert") ? new com.klpc.stadspring.domain.advert.entity.QAdvert(forProperty("advert"), inits.get("advert")) : null;
    }

}

