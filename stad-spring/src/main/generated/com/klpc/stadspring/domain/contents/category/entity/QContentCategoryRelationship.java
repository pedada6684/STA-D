package com.klpc.stadspring.domain.contents.category.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QContentCategoryRelationship is a Querydsl query type for ContentCategoryRelationship
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QContentCategoryRelationship extends EntityPathBase<ContentCategoryRelationship> {

    private static final long serialVersionUID = 333057498L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QContentCategoryRelationship contentCategoryRelationship = new QContentCategoryRelationship("contentCategoryRelationship");

    public final QContentCategory contentCategory;

    public final com.klpc.stadspring.domain.contents.detail.entity.QContentDetail contentDetail;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QContentCategoryRelationship(String variable) {
        this(ContentCategoryRelationship.class, forVariable(variable), INITS);
    }

    public QContentCategoryRelationship(Path<? extends ContentCategoryRelationship> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QContentCategoryRelationship(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QContentCategoryRelationship(PathMetadata metadata, PathInits inits) {
        this(ContentCategoryRelationship.class, metadata, inits);
    }

    public QContentCategoryRelationship(Class<? extends ContentCategoryRelationship> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.contentCategory = inits.isInitialized("contentCategory") ? new QContentCategory(forProperty("contentCategory")) : null;
        this.contentDetail = inits.isInitialized("contentDetail") ? new com.klpc.stadspring.domain.contents.detail.entity.QContentDetail(forProperty("contentDetail")) : null;
    }

}

