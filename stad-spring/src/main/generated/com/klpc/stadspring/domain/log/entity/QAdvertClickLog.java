package com.klpc.stadspring.domain.log.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAdvertClickLog is a Querydsl query type for AdvertClickLog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAdvertClickLog extends EntityPathBase<AdvertClickLog> {

    private static final long serialVersionUID = 2105182263L;

    public static final QAdvertClickLog advertClickLog = new QAdvertClickLog("advertClickLog");

    public final NumberPath<Long> advertId = createNumber("advertId", Long.class);

    public final NumberPath<Long> advertVideoId = createNumber("advertVideoId", Long.class);

    public final NumberPath<Long> contentId = createNumber("contentId", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.LocalDateTime> regDate = createDateTime("regDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QAdvertClickLog(String variable) {
        super(AdvertClickLog.class, forVariable(variable));
    }

    public QAdvertClickLog(Path<? extends AdvertClickLog> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAdvertClickLog(PathMetadata metadata) {
        super(AdvertClickLog.class, metadata);
    }

}

