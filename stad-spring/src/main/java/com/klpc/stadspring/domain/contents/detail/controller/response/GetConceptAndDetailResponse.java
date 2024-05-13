package com.klpc.stadspring.domain.contents.detail.controller.response;

import com.klpc.stadspring.domain.contents.concept.entity.ContentConcept;
import com.klpc.stadspring.domain.contents.detail.service.command.response.GetDetailListByConceptIdResponseCommand;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GetConceptAndDetailResponse {
    Long conceptId;
    boolean isMovie;
    String title;
    String thumbnailUrl;
    String playtime;
    String releaseYear;
    String audienceAge;
    String creator;
    String cast;
    String description;
    List<GetDetailListByConceptIdResponseCommand> data;

    public static GetConceptAndDetailResponse from(ContentConcept contentConcept, List<GetDetailListByConceptIdResponseCommand> commandList) {
        return GetConceptAndDetailResponse.builder()
                .conceptId(contentConcept.getId())
                .isMovie(contentConcept.isMovie())
                .title(contentConcept.getTitle())
                .thumbnailUrl(contentConcept.getThumbnailUrl())
                .playtime(contentConcept.getPlaytime())
                .releaseYear(contentConcept.getReleaseYear())
                .audienceAge(contentConcept.getAudienceAge())
                .creator(contentConcept.getCreator())
                .cast(contentConcept.getCast())
                .description(contentConcept.getDescription())
                .data(commandList)
                .build();
    }
}
