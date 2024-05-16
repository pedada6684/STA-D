package com.klpc.stadspring.domain.contents.concept.service.command.response;

import com.klpc.stadspring.domain.contents.concept.entity.ContentConcept;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetPopularContentResponseCommand {
    private String title;
    private String audienceAge;
    private String cast;
    private String creator;
    private String description;
    private String playtime;
    private String releaseYear;
    private String thumbnailUrl;
    private Long conceptId;

    public static GetPopularContentResponseCommand from(ContentConcept contentConcept) {
        return GetPopularContentResponseCommand.builder()
                .title(contentConcept.getTitle())
                .audienceAge(contentConcept.getAudienceAge())
                .cast(contentConcept.getCast())
                .creator(contentConcept.getCreator())
                .description(contentConcept.getDescription())
                .playtime(contentConcept.getPlaytime())
                .releaseYear(contentConcept.getReleaseYear())
                .thumbnailUrl(contentConcept.getThumbnailUrl())
                .conceptId(contentConcept.getId())
                .build();
    }
}
