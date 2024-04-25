package com.klpc.stadspring.domain.log.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QOrderLog is a Querydsl query type for OrderLog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrderLog extends EntityPathBase<OrderLog> {

    private static final long serialVersionUID = -194383363L;

    public static final QOrderLog orderLog = new QOrderLog("orderLog");

    public final NumberPath<Long> advertVideoId = createNumber("advertVideoId", Long.class);

    public final NumberPath<Long> contentId = createNumber("contentId", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> productId = createNumber("productId", Long.class);

    public final DateTimePath<java.time.LocalDateTime> regDate = createDateTime("regDate", java.time.LocalDateTime.class);

    public final BooleanPath status = createBoolean("status");

    public final DateTimePath<java.time.LocalDateTime> updateDate = createDateTime("updateDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QOrderLog(String variable) {
        super(OrderLog.class, forVariable(variable));
    }

    public QOrderLog(Path<? extends OrderLog> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOrderLog(PathMetadata metadata) {
        super(OrderLog.class, metadata);
    }

}

