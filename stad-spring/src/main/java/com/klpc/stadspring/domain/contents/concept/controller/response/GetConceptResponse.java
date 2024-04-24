package com.klpc.stadspring.domain.contents.concept.controller.response;

import com.klpc.stadspring.domain.contents.concept.entity.ContentConcept;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class GetConceptResponse {
    String title;
    String thumbnailUrl;
    Long conceptId;

    public static GetConceptResponse from(ContentConcept concept) {
        return GetConceptResponse.builder()
                .title(concept.getTitle())
                .thumbnailUrl(concept.getThumbnailUrl())
                .conceptId(concept.getId())
                .build();
    }
}
