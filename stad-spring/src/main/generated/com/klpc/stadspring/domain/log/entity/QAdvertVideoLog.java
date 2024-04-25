package com.klpc.stadspring.domain.log.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAdvertVideoLog is a Querydsl query type for AdvertVideoLog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAdvertVideoLog extends EntityPathBase<AdvertVideoLog> {

    private static final long serialVersionUID = -1944850332L;

    public static final QAdvertVideoLog advertVideoLog = new QAdvertVideoLog("advertVideoLog");

    public final NumberPath<Long> advertId = createNumber("advertId", Long.class);

    public final NumberPath<Long> advertVideoId = createNumber("advertVideoId", Long.class);

    public final NumberPath<Long> contentId = createNumber("contentId", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.LocalDateTime> regDate = createDateTime("regDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QAdvertVideoLog(String variable) {
        super(AdvertVideoLog.class, forVariable(variable));
    }

    public QAdvertVideoLog(Path<? extends AdvertVideoLog> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAdvertVideoLog(PathMetadata metadata) {
        super(AdvertVideoLog.class, metadata);
    }

}

