package com.klpc.stadspring.domain.contents.bookmark.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBookmarkedContent is a Querydsl query type for BookmarkedContent
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBookmarkedContent extends EntityPathBase<BookmarkedContent> {

    private static final long serialVersionUID = 1388681303L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBookmarkedContent bookmarkedContent = new QBookmarkedContent("bookmarkedContent");

    public final com.klpc.stadspring.domain.contents.detail.entity.QContentDetail contentDetail;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QBookmarkedContent(String variable) {
        this(BookmarkedContent.class, forVariable(variable), INITS);
    }

    public QBookmarkedContent(Path<? extends BookmarkedContent> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBookmarkedContent(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBookmarkedContent(PathMetadata metadata, PathInits inits) {
        this(BookmarkedContent.class, metadata, inits);
    }

    public QBookmarkedContent(Class<? extends BookmarkedContent> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.contentDetail = inits.isInitialized("contentDetail") ? new com.klpc.stadspring.domain.contents.detail.entity.QContentDetail(forProperty("contentDetail")) : null;
    }

}

