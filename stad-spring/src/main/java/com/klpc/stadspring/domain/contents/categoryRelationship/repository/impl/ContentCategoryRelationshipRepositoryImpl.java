package com.klpc.stadspring.domain.contents.categoryRelationship.repository.impl;

import static com.klpc.stadspring.domain.contents.categoryRelationship.entity.QContentCategoryRelationship.contentCategoryRelationship;

import com.klpc.stadspring.domain.contents.categoryRelationship.entity.ContentCategoryRelationship;
import com.klpc.stadspring.domain.contents.categoryRelationship.repository.custom.ContentCategoryRelationshipRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class ContentCategoryRelationshipRepositoryImpl implements ContentCategoryRelationshipRepositoryCustom {

    private final JPAQueryFactory query;

    @Override
    public Optional<List<Long>> findConceptIdByCategory(Long categoryId) {
        return Optional.ofNullable(query.select(contentCategoryRelationship.contentConcept.id)
                .from(contentCategoryRelationship)
                .where(contentCategoryRelationship.contentCategory.id.eq(categoryId))
                .limit(12)
                .fetch());
    }

    @Override
    public Optional<ContentCategoryRelationship> findByCategoryAndConcept(Long categoryId, Long conceptId) {
        return Optional.ofNullable(query.select(contentCategoryRelationship)
                .from(contentCategoryRelationship)
                .where(contentCategoryRelationship.contentCategory.id.eq(categoryId)
                        .and(contentCategoryRelationship.contentConcept.id.eq(conceptId)))
                .fetchOne());
    }
}
