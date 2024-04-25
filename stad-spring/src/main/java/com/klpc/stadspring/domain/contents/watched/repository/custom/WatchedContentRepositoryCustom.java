package com.klpc.stadspring.domain.contents.watched.repository.custom;

import java.util.List;
import java.util.Optional;

public interface WatchedContentRepositoryCustom {
    Optional<List<Long>> findDetailIdByUserId(Long id);
}
