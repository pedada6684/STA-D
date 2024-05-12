package com.klpc.stadspring.domain.contents.detail.controller.response;

import com.klpc.stadspring.domain.contents.concept.entity.ContentConcept;
import com.klpc.stadspring.domain.contents.detail.entity.ContentDetail;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetThumbnailResponse {
    String title;
    String thumbnailUrl;
    Long conceptId;

    public static GetThumbnailResponse from(ContentConcept concept) {
        return GetThumbnailResponse.builder()
                .title(concept.getTitle())
                .thumbnailUrl(concept.getThumbnailUrl())
                .conceptId(concept.getId())
                .build();
    }
}
