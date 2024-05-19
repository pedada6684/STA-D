package com.klpc.stadspring.domain.contents.concept.repository.impl;

import static com.klpc.stadspring.domain.contents.concept.entity.QContentConcept.contentConcept;

import com.klpc.stadspring.domain.contents.concept.entity.ContentConcept;
import com.klpc.stadspring.domain.contents.concept.repository.custom.ContentConceptRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ContentConceptRepositoryImpl implements ContentConceptRepositoryCustom {

    public final JPAQueryFactory query;

    @Override
    public Optional<List<ContentConcept>> findByKeyword(String keyword) {
        return Optional.ofNullable(query.select(contentConcept)
                .from(contentConcept)
                .where(contentConcept.title.contains(keyword))
                .fetch());
    }

    @Override
    public Optional<ContentConcept> findByIsMovieAndTitle(boolean isMovie, String title) {
        return Optional.ofNullable(query.select(contentConcept)
                .from(contentConcept)
                .where(contentConcept.isMovie.eq(isMovie)
                        .and(contentConcept.title.eq(title)))
                .fetchOne());
    }
}
