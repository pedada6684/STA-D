package com.klpc.stadspring.domain.contents.categoryRelationship.repository.custom;


import java.util.List;
import java.util.Optional;

public interface ContentCategoryRelationshipRepositoryCustom {
    Optional<List<Long>> findConceptIdByCategory(Long categoryId);
}
