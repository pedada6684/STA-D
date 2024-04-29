package com.klpc.stadspring.domain.advertVideo.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAdvertVideo is a Querydsl query type for AdvertVideo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAdvertVideo extends EntityPathBase<AdvertVideo> {

    private static final long serialVersionUID = 1856273949L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAdvertVideo advertVideo = new QAdvertVideo("advertVideo");

    public final com.klpc.stadspring.domain.advert.entity.QAdvert advert;

    public final NumberPath<Long> clickCnt = createNumber("clickCnt", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> len = createNumber("len", Long.class);

    public final NumberPath<Long> spreadCnt = createNumber("spreadCnt", Long.class);

    public final StringPath videoUrl = createString("videoUrl");

    public QAdvertVideo(String variable) {
        this(AdvertVideo.class, forVariable(variable), INITS);
    }

    public QAdvertVideo(Path<? extends AdvertVideo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAdvertVideo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAdvertVideo(PathMetadata metadata, PathInits inits) {
        this(AdvertVideo.class, metadata, inits);
    }

    public QAdvertVideo(Class<? extends AdvertVideo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.advert = inits.isInitialized("advert") ? new com.klpc.stadspring.domain.advert.entity.QAdvert(forProperty("advert"), inits.get("advert")) : null;
    }

}

