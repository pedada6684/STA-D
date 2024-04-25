package com.klpc.stadspring.domain.advert.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAdvert is a Querydsl query type for Advert
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAdvert extends EntityPathBase<Advert> {

    private static final long serialVersionUID = -820311361L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAdvert advert = new QAdvert("advert");

    public final EnumPath<AdvertType> advertType = createEnum("advertType", AdvertType.class);

    public final ListPath<com.klpc.stadspring.domain.advertVideo.entity.AdvertVideo, com.klpc.stadspring.domain.advertVideo.entity.QAdvertVideo> advertVideos = this.<com.klpc.stadspring.domain.advertVideo.entity.AdvertVideo, com.klpc.stadspring.domain.advertVideo.entity.QAdvertVideo>createList("advertVideos", com.klpc.stadspring.domain.advertVideo.entity.AdvertVideo.class, com.klpc.stadspring.domain.advertVideo.entity.QAdvertVideo.class, PathInits.DIRECT2);

    public final StringPath bannerImgUrl = createString("bannerImgUrl");

    public final StringPath category = createString("category");

    public final StringPath description = createString("description");

    public final StringPath directVideoUrl = createString("directVideoUrl");

    public final DateTimePath<java.time.LocalDateTime> endDate = createDateTime("endDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<com.klpc.stadspring.domain.product.entity.Product, com.klpc.stadspring.domain.product.entity.QProduct> products = this.<com.klpc.stadspring.domain.product.entity.Product, com.klpc.stadspring.domain.product.entity.QProduct>createList("products", com.klpc.stadspring.domain.product.entity.Product.class, com.klpc.stadspring.domain.product.entity.QProduct.class, PathInits.DIRECT2);

    public final ListPath<com.klpc.stadspring.domain.selectedContent.entity.SelectedContent, com.klpc.stadspring.domain.selectedContent.entity.QSelectedContent> selectedContents = this.<com.klpc.stadspring.domain.selectedContent.entity.SelectedContent, com.klpc.stadspring.domain.selectedContent.entity.QSelectedContent>createList("selectedContents", com.klpc.stadspring.domain.selectedContent.entity.SelectedContent.class, com.klpc.stadspring.domain.selectedContent.entity.QSelectedContent.class, PathInits.DIRECT2);

    public final DateTimePath<java.time.LocalDateTime> startDate = createDateTime("startDate", java.time.LocalDateTime.class);

    public final StringPath title = createString("title");

    public final com.klpc.stadspring.domain.user.entity.QUser user;

    public QAdvert(String variable) {
        this(Advert.class, forVariable(variable), INITS);
    }

    public QAdvert(Path<? extends Advert> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAdvert(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAdvert(PathMetadata metadata, PathInits inits) {
        this(Advert.class, metadata, inits);
    }

    public QAdvert(Class<? extends Advert> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.klpc.stadspring.domain.user.entity.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

