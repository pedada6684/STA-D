package com.klpc.stadspring.domain.contents.bookmark.repository.custom;

import com.klpc.stadspring.domain.contents.bookmark.entity.BookmarkedContent;
import com.klpc.stadspring.domain.contents.detail.entity.ContentDetail;

import java.util.List;
import java.util.Optional;

public interface BookmarkedContentRepositoryCustom {
    Optional<List<Long>> findConceptIdByUserId(Long userId);
}
