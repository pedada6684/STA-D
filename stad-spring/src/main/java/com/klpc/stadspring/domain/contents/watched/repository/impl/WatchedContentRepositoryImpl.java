package com.klpc.stadspring.domain.contents.watched.repository.impl;

import static com.klpc.stadspring.domain.contents.watched.entity.QWatchedContent.watchedContent;

import com.klpc.stadspring.domain.contents.watched.entity.WatchedContent;
import com.klpc.stadspring.domain.contents.watched.repository.custom.WatchedContentRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class WatchedContentRepositoryImpl implements WatchedContentRepositoryCustom {
    private final JPAQueryFactory query;

    @Override
    public Optional<List<Long>> findWatchingContentDetailIdByUserId(Long userId) {
//        return Optional.ofNullable(query.select(watchedContent.contentDetail.id)
//                .from(watchedContent)
//                .where(watchedContent.user.id.eq(userId)
//                        .and(watchedContent.status.isFalse()))
//                .orderBy(watchedContent.dateTime.desc())
//                .fetch());
        return Optional.empty();
    }

    @Override
    public Optional<List<Long>> findWatchingAndWatchedContentDetailIdByUserId(Long userId) {
//        return Optional.ofNullable(query.select(watchedContent.contentDetail.id)
//                .from(watchedContent)
//                .where(watchedContent.user.id.eq(userId))
//                .orderBy(watchedContent.dateTime.desc())
//                .fetch());
        return Optional.empty();
    }

    @Override
    public Optional<WatchedContent> findByUserIdAndDetailId(Long userId, Long detailId) {
        return Optional.ofNullable(query.select(watchedContent)
                .from(watchedContent)
                .where(watchedContent.user.id.eq(userId)
                        .and(watchedContent.contentDetail.id.eq(detailId)))
                .fetchOne());
    }

    @Override
    public Long updateWatchedContent(WatchedContent updatedWatchedContent) {
        return query.update(watchedContent)
                .set(watchedContent.dateTime, updatedWatchedContent.getDateTime())
                .set(watchedContent.status, updatedWatchedContent.isStatus())
                .set(watchedContent.stopTime, updatedWatchedContent.getStopTime())
                .where(watchedContent.id.eq(updatedWatchedContent.getId()))
                .execute();
    }
}
