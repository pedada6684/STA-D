package com.klpc.stadspring.domain.contents.concept.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QContentConcept is a Querydsl query type for ContentConcept
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QContentConcept extends EntityPathBase<ContentConcept> {

    private static final long serialVersionUID = -487911718L;

    public static final QContentConcept contentConcept = new QContentConcept("contentConcept");

    public final StringPath audienceAge = createString("audienceAge");

    public final StringPath cast = createString("cast");

    public final StringPath creator = createString("creator");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isMovie = createBoolean("isMovie");

    public final StringPath thumbnailUrl = createString("thumbnailUrl");

    public QContentConcept(String variable) {
        super(ContentConcept.class, forVariable(variable));
    }

    public QContentConcept(Path<? extends ContentConcept> path) {
        super(path.getType(), path.getMetadata());
    }

    public QContentConcept(PathMetadata metadata) {
        super(ContentConcept.class, metadata);
    }

}

