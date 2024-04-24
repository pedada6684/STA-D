package com.klpc.stadspring.domain.orders.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrders is a Querydsl query type for Orders
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrders extends EntityPathBase<Orders> {

    private static final long serialVersionUID = -1513504991L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrders orders = new QOrders("orders");

    public final NumberPath<Long> advertId = createNumber("advertId", Long.class);

    public final NumberPath<Long> contentId = createNumber("contentId", Long.class);

    public final com.klpc.stadspring.domain.delivery.entity.QDelivery delivery;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.LocalDateTime> orderDate = createDateTime("orderDate", java.time.LocalDateTime.class);

    public final ListPath<com.klpc.stadspring.domain.orderProduct.entity.OrderProduct, com.klpc.stadspring.domain.orderProduct.entity.QOrderProduct> orderProducts = this.<com.klpc.stadspring.domain.orderProduct.entity.OrderProduct, com.klpc.stadspring.domain.orderProduct.entity.QOrderProduct>createList("orderProducts", com.klpc.stadspring.domain.orderProduct.entity.OrderProduct.class, com.klpc.stadspring.domain.orderProduct.entity.QOrderProduct.class, PathInits.DIRECT2);

    public final EnumPath<OrderStatus> status = createEnum("status", OrderStatus.class);

    public final com.klpc.stadspring.domain.user.entity.QUser user;

    public QOrders(String variable) {
        this(Orders.class, forVariable(variable), INITS);
    }

    public QOrders(Path<? extends Orders> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrders(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrders(PathMetadata metadata, PathInits inits) {
        this(Orders.class, metadata, inits);
    }

    public QOrders(Class<? extends Orders> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.delivery = inits.isInitialized("delivery") ? new com.klpc.stadspring.domain.delivery.entity.QDelivery(forProperty("delivery"), inits.get("delivery")) : null;
        this.user = inits.isInitialized("user") ? new com.klpc.stadspring.domain.user.entity.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

