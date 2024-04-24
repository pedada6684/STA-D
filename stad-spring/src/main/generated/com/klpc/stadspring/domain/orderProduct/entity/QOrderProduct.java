package com.klpc.stadspring.domain.orderProduct.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrderProduct is a Querydsl query type for OrderProduct
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrderProduct extends EntityPathBase<OrderProduct> {

    private static final long serialVersionUID = 1536697945L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrderProduct orderProduct = new QOrderProduct("orderProduct");

    public final NumberPath<Long> cnt = createNumber("cnt", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.klpc.stadspring.domain.product.entity.QProduct product;

    public final com.klpc.stadspring.domain.productOrder.entity.QProductOrder productOrder;

    public QOrderProduct(String variable) {
        this(OrderProduct.class, forVariable(variable), INITS);
    }

    public QOrderProduct(Path<? extends OrderProduct> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrderProduct(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrderProduct(PathMetadata metadata, PathInits inits) {
        this(OrderProduct.class, metadata, inits);
    }

    public QOrderProduct(Class<? extends OrderProduct> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.product = inits.isInitialized("product") ? new com.klpc.stadspring.domain.product.entity.QProduct(forProperty("product"), inits.get("product")) : null;
        this.productOrder = inits.isInitialized("productOrder") ? new com.klpc.stadspring.domain.productOrder.entity.QProductOrder(forProperty("productOrder"), inits.get("productOrder")) : null;
    }

}

