package com.klpc.stadspring.domain.contents.concept.repository.custom;

import com.klpc.stadspring.domain.contents.concept.entity.ContentConcept;

import java.util.List;
import java.util.Optional;

public interface ContentConceptRepositoryCustom {
    Optional<List<ContentConcept>> findByKeyword(String keyword);
    Optional<ContentConcept> findByIsMovieAndTitle(boolean isMovie, String title);
    Optional<List<ContentConcept>> findPopularContent();
}
