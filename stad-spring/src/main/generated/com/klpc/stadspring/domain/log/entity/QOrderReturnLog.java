package com.klpc.stadspring.domain.log.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QOrderReturnLog is a Querydsl query type for OrderReturnLog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrderReturnLog extends EntityPathBase<OrderReturnLog> {

    private static final long serialVersionUID = 798638061L;

    public static final QOrderReturnLog orderReturnLog = new QOrderReturnLog("orderReturnLog");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> orderId = createNumber("orderId", Long.class);

    public final DateTimePath<java.time.LocalDateTime> regDate = createDateTime("regDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QOrderReturnLog(String variable) {
        super(OrderReturnLog.class, forVariable(variable));
    }

    public QOrderReturnLog(Path<? extends OrderReturnLog> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOrderReturnLog(PathMetadata metadata) {
        super(OrderReturnLog.class, metadata);
    }

}

