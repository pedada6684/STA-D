package com.klpc.stadspring.domain.contents.category.repository;

import com.klpc.stadspring.domain.contents.category.entity.ContentCategory;
import com.klpc.stadspring.domain.contents.category.repository.custom.ContentCategoryRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentCategoryRepository extends JpaRepository<ContentCategory, Long>, ContentCategoryRepositoryCustom {
}
