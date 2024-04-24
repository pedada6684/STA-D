package com.klpc.stadspring.domain.contents.watched.repository.impl;

import static com.klpc.stadspring.domain.contents.watched.entity.QWatchedContent.watchedContent;
import com.klpc.stadspring.domain.contents.watched.repository.custom.WatchedContentRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class WatchedContentRepositoryImpl implements WatchedContentRepositoryCustom {
    private final JPAQueryFactory query;

    @Override
    public Optional<List<Long>> findDetailIdByUserId(Long id) {
        return Optional.ofNullable(query.select(watchedContent.contentDetail.id)
                .from(watchedContent)
                .where(watchedContent.userId.eq(id)
                        .and(watchedContent.status.isFalse()))
                .fetch());
    }
}
