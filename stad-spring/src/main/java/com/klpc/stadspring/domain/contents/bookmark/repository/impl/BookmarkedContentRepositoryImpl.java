package com.klpc.stadspring.domain.contents.bookmark.repository.impl;

import static com.klpc.stadspring.domain.contents.bookmark.entity.QBookmarkedContent.bookmarkedContent;

import com.klpc.stadspring.domain.contents.bookmark.entity.BookmarkedContent;
import com.klpc.stadspring.domain.contents.bookmark.repository.custom.BookmarkedContentRepositoryCustom;
import com.klpc.stadspring.domain.contents.detail.entity.ContentDetail;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class BookmarkedContentRepositoryImpl implements BookmarkedContentRepositoryCustom {
    private final JPAQueryFactory query;

    @Override
    public Optional<List<Long>> findDetailIdByUserId(Long userId) {
        return Optional.ofNullable(query.select(bookmarkedContent.contentDetail.id)
                .from(bookmarkedContent)
                .where(bookmarkedContent.user.id.eq(userId))
                .fetch());
    }

    @Override
    public Optional<BookmarkedContent> findByUserIdAndContentDetail(Long userId, Long contentId) {
        return Optional.ofNullable(query.select(bookmarkedContent)
                .from(bookmarkedContent)
                .where(bookmarkedContent.user.id.eq(userId)
                        .and(bookmarkedContent.contentDetail.id.eq(contentId)))
                .fetchOne());
    }
}
