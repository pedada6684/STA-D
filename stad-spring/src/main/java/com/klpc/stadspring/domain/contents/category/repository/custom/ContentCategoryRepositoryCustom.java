package com.klpc.stadspring.domain.contents.category.repository.custom;

import com.klpc.stadspring.domain.contents.category.entity.ContentCategory;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ContentCategoryRepositoryCustom {
    Optional<List<String>> findNameByIsMovie(boolean isMovie);
    Optional<Long> findIdByIsMovieAndName(boolean isMovie, String name);
    Optional<List<Long>> findIdByIsMovie(boolean isMovie);
    Optional<ContentCategory> findByIsMovieAndName(boolean isMovie, String name);

    Optional<List<String>> findAllNames();
}
