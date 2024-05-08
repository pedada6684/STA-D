package com.klpc.stadspring.domain.contents.categoryRelationship.repository.custom;


import com.klpc.stadspring.domain.contents.categoryRelationship.entity.ContentCategoryRelationship;

import java.util.List;
import java.util.Optional;

public interface ContentCategoryRelationshipRepositoryCustom {
    Optional<List<Long>> findConceptIdByCategory(Long categoryId);
    Optional<ContentCategoryRelationship> findByCategoryAndConcept(Long categoryId, Long conceptId);
}
