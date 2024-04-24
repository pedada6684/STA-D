package com.klpc.stadspring.domain.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserYoutubeInfo is a Querydsl query type for UserYoutubeInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserYoutubeInfo extends EntityPathBase<UserYoutubeInfo> {

    private static final long serialVersionUID = -431092028L;

    public static final QUserYoutubeInfo userYoutubeInfo = new QUserYoutubeInfo("userYoutubeInfo");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath youtubeInfo = createString("youtubeInfo");

    public QUserYoutubeInfo(String variable) {
        super(UserYoutubeInfo.class, forVariable(variable));
    }

    public QUserYoutubeInfo(Path<? extends UserYoutubeInfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserYoutubeInfo(PathMetadata metadata) {
        super(UserYoutubeInfo.class, metadata);
    }

}

