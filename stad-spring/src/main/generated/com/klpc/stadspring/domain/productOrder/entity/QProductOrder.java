package com.klpc.stadspring.domain.productOrder.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductOrder is a Querydsl query type for ProductOrder
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductOrder extends EntityPathBase<ProductOrder> {

    private static final long serialVersionUID = 983844629L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductOrder productOrder = new QProductOrder("productOrder");

    public final NumberPath<Long> advertId = createNumber("advertId", Long.class);

    public final NumberPath<Long> contentId = createNumber("contentId", Long.class);

    public final com.klpc.stadspring.domain.delivery.entity.QDelivery delivery;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.LocalDateTime> orderDate = createDateTime("orderDate", java.time.LocalDateTime.class);

    public final ListPath<com.klpc.stadspring.domain.orderProduct.entity.OrderProduct, com.klpc.stadspring.domain.orderProduct.entity.QOrderProduct> orderProducts = this.<com.klpc.stadspring.domain.orderProduct.entity.OrderProduct, com.klpc.stadspring.domain.orderProduct.entity.QOrderProduct>createList("orderProducts", com.klpc.stadspring.domain.orderProduct.entity.OrderProduct.class, com.klpc.stadspring.domain.orderProduct.entity.QOrderProduct.class, PathInits.DIRECT2);

    public final EnumPath<OrderStatus> status = createEnum("status", OrderStatus.class);

    public final com.klpc.stadspring.domain.user.entity.QUser user;

    public QProductOrder(String variable) {
        this(ProductOrder.class, forVariable(variable), INITS);
    }

    public QProductOrder(Path<? extends ProductOrder> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductOrder(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductOrder(PathMetadata metadata, PathInits inits) {
        this(ProductOrder.class, metadata, inits);
    }

    public QProductOrder(Class<? extends ProductOrder> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.delivery = inits.isInitialized("delivery") ? new com.klpc.stadspring.domain.delivery.entity.QDelivery(forProperty("delivery"), inits.get("delivery")) : null;
        this.user = inits.isInitialized("user") ? new com.klpc.stadspring.domain.user.entity.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

