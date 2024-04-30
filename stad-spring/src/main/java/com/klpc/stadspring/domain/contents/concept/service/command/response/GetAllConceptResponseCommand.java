package com.klpc.stadspring.domain.contents.concept.service.command.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetAllConceptResponseCommand {
    private Long id;
    private boolean isMovie;
    private String title;
    private String thumbnailUrl;
    private String releaseYear;
    private String audienceAge;
    private String creator;
    private String cast;
    private String playtime;
    private String description;
}
