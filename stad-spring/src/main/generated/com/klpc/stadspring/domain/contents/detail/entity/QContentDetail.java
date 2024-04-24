package com.klpc.stadspring.domain.contents.detail.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QContentDetail is a Querydsl query type for ContentDetail
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QContentDetail extends EntityPathBase<ContentDetail> {

    private static final long serialVersionUID = -1461265694L;

    public static final QContentDetail contentDetail = new QContentDetail("contentDetail");

    public final ListPath<com.klpc.stadspring.domain.contents.bookmark.entity.BookmarkedContent, com.klpc.stadspring.domain.contents.bookmark.entity.QBookmarkedContent> bookmarkedContentList = this.<com.klpc.stadspring.domain.contents.bookmark.entity.BookmarkedContent, com.klpc.stadspring.domain.contents.bookmark.entity.QBookmarkedContent>createList("bookmarkedContentList", com.klpc.stadspring.domain.contents.bookmark.entity.BookmarkedContent.class, com.klpc.stadspring.domain.contents.bookmark.entity.QBookmarkedContent.class, PathInits.DIRECT2);

    public final ListPath<com.klpc.stadspring.domain.contents.category.entity.ContentCategoryRelationship, com.klpc.stadspring.domain.contents.category.entity.QContentCategoryRelationship> contentCategoryRelationshipList = this.<com.klpc.stadspring.domain.contents.category.entity.ContentCategoryRelationship, com.klpc.stadspring.domain.contents.category.entity.QContentCategoryRelationship>createList("contentCategoryRelationshipList", com.klpc.stadspring.domain.contents.category.entity.ContentCategoryRelationship.class, com.klpc.stadspring.domain.contents.category.entity.QContentCategoryRelationship.class, PathInits.DIRECT2);

    public final NumberPath<Long> contentConceptId = createNumber("contentConceptId", Long.class);

    public final NumberPath<Integer> episode = createNumber("episode", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath playtime = createString("playtime");

    public final StringPath videoUrl = createString("videoUrl");

    public final ListPath<com.klpc.stadspring.domain.contents.watched.entity.WatchedContent, com.klpc.stadspring.domain.contents.watched.entity.QWatchedContent> watchedContentList = this.<com.klpc.stadspring.domain.contents.watched.entity.WatchedContent, com.klpc.stadspring.domain.contents.watched.entity.QWatchedContent>createList("watchedContentList", com.klpc.stadspring.domain.contents.watched.entity.WatchedContent.class, com.klpc.stadspring.domain.contents.watched.entity.QWatchedContent.class, PathInits.DIRECT2);

    public QContentDetail(String variable) {
        super(ContentDetail.class, forVariable(variable));
    }

    public QContentDetail(Path<? extends ContentDetail> path) {
        super(path.getType(), path.getMetadata());
    }

    public QContentDetail(PathMetadata metadata) {
        super(ContentDetail.class, metadata);
    }

}

