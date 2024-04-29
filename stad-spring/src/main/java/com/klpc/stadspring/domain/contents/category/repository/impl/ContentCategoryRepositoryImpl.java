package com.klpc.stadspring.domain.contents.category.repository.impl;

import static com.klpc.stadspring.domain.contents.category.entity.QContentCategory.contentCategory;

import com.klpc.stadspring.domain.contents.category.entity.ContentCategory;
import com.klpc.stadspring.domain.contents.category.repository.custom.ContentCategoryRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class ContentCategoryRepositoryImpl implements ContentCategoryRepositoryCustom {

    private final JPAQueryFactory query;

    @Override
    public Optional<List<String>> findNameByIsMovie(boolean isMovie) {
        return Optional.ofNullable(query.select(contentCategory.name)
                .from(contentCategory)
                .where(contentCategory.isMovie.eq(isMovie))
                .fetch());
    }

    @Override
    public Optional<Long> findIdByIsMovieAndName(boolean isMovie, String name) {
        return Optional.ofNullable(query.select(contentCategory.id)
                .from(contentCategory)
                .where(contentCategory.isMovie.eq(isMovie)
                        .and(contentCategory.name.eq(name)))
                .fetchOne());
    }

    @Override
    public Optional<ContentCategory> findByIsMovieAndName(boolean isMovie, String name) {
        return Optional.ofNullable(query.select(contentCategory)
                .from(contentCategory)
                .where(contentCategory.isMovie.eq(isMovie)
                        .and(contentCategory.name.eq(name)))
                .fetchOne());
    }
}
