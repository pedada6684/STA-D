package com.klpc.stadspring.domain.contents.detail.controller.response;

import com.klpc.stadspring.domain.contents.concept.entity.ContentConcept;
import com.klpc.stadspring.domain.contents.detail.entity.ContentDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Builder
public class GetDetailIdAndThumbnailResponse {
    String title;
    String thumbnailUrl;
    Long detailId;

    public static GetDetailIdAndThumbnailResponse from(ContentDetail detail, ContentConcept concept) {
        return GetDetailIdAndThumbnailResponse.builder()
                .title(concept.getTitle())
                .thumbnailUrl(concept.getThumbnailUrl())
                .detailId(detail.getId())
                .build();
    }
}
