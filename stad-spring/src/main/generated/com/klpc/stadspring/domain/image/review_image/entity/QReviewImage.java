package com.klpc.stadspring.domain.image.review_image.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReviewImage is a Querydsl query type for ReviewImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReviewImage extends EntityPathBase<ReviewImage> {

    private static final long serialVersionUID = -676488859L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReviewImage reviewImage = new QReviewImage("reviewImage");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath img = createString("img");

    public final com.klpc.stadspring.domain.product_review.entity.QProductReview productReview;

    public QReviewImage(String variable) {
        this(ReviewImage.class, forVariable(variable), INITS);
    }

    public QReviewImage(Path<? extends ReviewImage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReviewImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReviewImage(PathMetadata metadata, PathInits inits) {
        this(ReviewImage.class, metadata, inits);
    }

    public QReviewImage(Class<? extends ReviewImage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.productReview = inits.isInitialized("productReview") ? new com.klpc.stadspring.domain.product_review.entity.QProductReview(forProperty("productReview"), inits.get("productReview")) : null;
    }

}

