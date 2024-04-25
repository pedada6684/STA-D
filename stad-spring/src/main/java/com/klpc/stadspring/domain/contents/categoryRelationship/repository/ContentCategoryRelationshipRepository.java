package com.klpc.stadspring.domain.contents.categoryRelationship.repository;

import com.klpc.stadspring.domain.contents.categoryRelationship.entity.ContentCategoryRelationship;
import com.klpc.stadspring.domain.contents.categoryRelationship.repository.custom.ContentCategoryRelationshipRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentCategoryRelationshipRepository extends JpaRepository<ContentCategoryRelationship, Long>, ContentCategoryRelationshipRepositoryCustom {
}
