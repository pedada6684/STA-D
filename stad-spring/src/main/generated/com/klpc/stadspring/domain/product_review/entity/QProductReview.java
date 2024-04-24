package com.klpc.stadspring.domain.product_review.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductReview is a Querydsl query type for ProductReview
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductReview extends EntityPathBase<ProductReview> {

    private static final long serialVersionUID = -450813560L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductReview productReview = new QProductReview("productReview");

    public final StringPath content = createString("content");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.klpc.stadspring.domain.product.entity.QProduct product;

    public final DateTimePath<java.time.LocalDateTime> regDate = createDateTime("regDate", java.time.LocalDateTime.class);

    public final StringPath reviewImg = createString("reviewImg");

    public final NumberPath<Double> score = createNumber("score", Double.class);

    public final StringPath title = createString("title");

    public final com.klpc.stadspring.domain.user.entity.QUser user;

    public QProductReview(String variable) {
        this(ProductReview.class, forVariable(variable), INITS);
    }

    public QProductReview(Path<? extends ProductReview> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductReview(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductReview(PathMetadata metadata, PathInits inits) {
        this(ProductReview.class, metadata, inits);
    }

    public QProductReview(Class<? extends ProductReview> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.product = inits.isInitialized("product") ? new com.klpc.stadspring.domain.product.entity.QProduct(forProperty("product"), inits.get("product")) : null;
        this.user = inits.isInitialized("user") ? new com.klpc.stadspring.domain.user.entity.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

