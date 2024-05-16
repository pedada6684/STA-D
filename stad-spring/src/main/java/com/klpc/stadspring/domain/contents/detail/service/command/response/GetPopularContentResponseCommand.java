package com.klpc.stadspring.domain.contents.detail.service.command.response;

import com.klpc.stadspring.domain.contents.concept.entity.ContentConcept;
import com.klpc.stadspring.domain.contents.detail.entity.ContentDetail;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetPopularContentResponseCommand {
    private String title;
    private String episode;
    private String audienceAge;
    private String cast;
    private String creator;
    private String description;
    private String playtime;
    private String releaseYear;
    private String thumbnailUrl;
    private Long conceptId;

    public static GetPopularContentResponseCommand from(ContentDetail contentDetail, ContentConcept contentConcept) {
        return GetPopularContentResponseCommand.builder()
                .title(contentConcept.getTitle())
                .episode(contentDetail.getEpisode() != null ? contentDetail.getEpisode()+"화" : "영화")
                .audienceAge(contentConcept.getAudienceAge())
                .cast(contentConcept.getCast())
                .creator(contentConcept.getCreator())
                .description(contentDetail.getSummary() != null ? contentDetail.getSummary() : contentConcept.getDescription())
                .playtime(contentConcept.getPlaytime())
                .releaseYear(contentConcept.getReleaseYear())
                .thumbnailUrl(contentConcept.getThumbnailUrl())
                .conceptId(contentConcept.getId())
                .build();
    }
}
