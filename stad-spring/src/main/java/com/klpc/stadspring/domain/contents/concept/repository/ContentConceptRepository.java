package com.klpc.stadspring.domain.contents.concept.repository;

import com.klpc.stadspring.domain.contents.concept.entity.ContentConcept;
import com.klpc.stadspring.domain.contents.concept.repository.custom.ContentConceptRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentConceptRepository extends JpaRepository<ContentConcept, Long>, ContentConceptRepositoryCustom {
}
