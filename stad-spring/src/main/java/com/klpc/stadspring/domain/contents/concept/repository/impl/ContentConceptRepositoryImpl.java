package com.klpc.stadspring.domain.contents.concept.repository.impl;

import static com.klpc.stadspring.domain.contents.concept.entity.QContentConcept.contentConcept;

import com.klpc.stadspring.domain.contents.concept.entity.ContentConcept;
import com.klpc.stadspring.domain.contents.concept.repository.custom.ContentConceptRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ContentConceptRepositoryImpl implements ContentConceptRepositoryCustom {
    public final JPAQueryFactory query;

    @Override
    public Optional<ContentConcept> findById(Long id) {
        return Optional.ofNullable(query.select(contentConcept)
                .from(contentConcept)
                .where(contentConcept.id.eq(id))
                .fetchOne());
    }
}
