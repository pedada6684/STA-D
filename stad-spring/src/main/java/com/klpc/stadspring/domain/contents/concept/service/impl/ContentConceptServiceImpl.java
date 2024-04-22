package com.klpc.stadspring.domain.contents.concept.service.impl;

import com.klpc.stadspring.domain.contents.concept.entity.ContentConcept;
import com.klpc.stadspring.domain.contents.concept.repository.ContentConceptRepository;
import com.klpc.stadspring.domain.contents.concept.service.ContentConceptService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ContentConceptServiceImpl implements ContentConceptService {
    public final ContentConceptRepository repository;

    @Override
    public ContentConcept getContentConceptById(Long id) {
        Optional<ContentConcept> contentConcept = repository.findById(id);
        if (contentConcept.isPresent()) {
            ContentConcept result = contentConcept.get().builder()
                    .id(contentConcept.get().getId())
                    .isMovie(contentConcept.get().isMovie())
                    .thumbnailUrl(contentConcept.get().getThumbnailUrl())
                    .audienceAge(contentConcept.get().getAudienceAge())
                    .creator(contentConcept.get().getCreator())
                    .cast(contentConcept.get().getCast())
                    .build();
            return result;
        }
        return null;
    }
}
