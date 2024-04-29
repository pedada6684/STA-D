package com.klpc.stadspring.domain.contents.detail.controller.response;

import com.klpc.stadspring.domain.contents.concept.entity.ContentConcept;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class GetContentConceptResponse {
    String title;
    String thumbnailUrl;
    String playtime;
    String releaseYear;
    String audienceAge;
    String creator;
    String cast;
    String description;

    public static GetContentConceptResponse from(ContentConcept contentConcept) {
        return GetContentConceptResponse.builder()
                .title(contentConcept.getTitle())
                .thumbnailUrl(contentConcept.getThumbnailUrl())
                .playtime(contentConcept.getPlaytime())
                .releaseYear(contentConcept.getReleaseYear())
                .audienceAge(contentConcept.getAudienceAge())
                .creator(contentConcept.getCreator())
                .cast(contentConcept.getCast())
                .description(contentConcept.getDescription())
                .build();
    }
}
