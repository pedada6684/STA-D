package com.klpc.stadspring.domain.contents.watched.repository.custom;

import com.klpc.stadspring.domain.contents.watched.entity.WatchedContent;

import java.util.List;
import java.util.Optional;

public interface WatchedContentRepositoryCustom {
    Optional<List<WatchedContent>> findAllByUserId(Long userId);
    Optional<List<Long>> findDetailIdByUserId(Long userId);
    Optional<WatchedContent> findByUserIdAndDetailId(Long userId, Long detailId);
    Long updateWatchedContent(WatchedContent updatedWatchedContent);
}
