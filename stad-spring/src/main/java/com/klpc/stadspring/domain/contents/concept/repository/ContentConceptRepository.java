package com.klpc.stadspring.domain.contents.concept.repository;

import com.klpc.stadspring.domain.contents.concept.entity.ContentConcept;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentConceptRepository extends JpaRepository<ContentConcept, Long> {
}
