package com.klpc.stadspring.domain.contents.watched.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWatchedContent is a Querydsl query type for WatchedContent
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWatchedContent extends EntityPathBase<WatchedContent> {

    private static final long serialVersionUID = 1821627612L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWatchedContent watchedContent = new QWatchedContent("watchedContent");

    public final com.klpc.stadspring.domain.contents.detail.entity.QContentDetail contentDetail;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath status = createBoolean("status");

    public final StringPath stopTime = createString("stopTime");

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public final StringPath watchedDate = createString("watchedDate");

    public QWatchedContent(String variable) {
        this(WatchedContent.class, forVariable(variable), INITS);
    }

    public QWatchedContent(Path<? extends WatchedContent> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWatchedContent(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWatchedContent(PathMetadata metadata, PathInits inits) {
        this(WatchedContent.class, metadata, inits);
    }

    public QWatchedContent(Class<? extends WatchedContent> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.contentDetail = inits.isInitialized("contentDetail") ? new com.klpc.stadspring.domain.contents.detail.entity.QContentDetail(forProperty("contentDetail")) : null;
    }

}

