package com.klpc.stadspring.domain.contents.concept.repository.custom;

import com.klpc.stadspring.domain.contents.concept.entity.ContentConcept;

import java.util.Optional;

public interface ContentConceptRepositoryCustom {
    Optional<ContentConcept> findById(Long id);
}
