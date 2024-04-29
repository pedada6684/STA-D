package com.klpc.stadspring.domain.contents.concept.service.command.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ParseJsonCommand {
    private String audienceAge;
    private String playtime;
    private String description;
    private String cast;
    private String creator;
    private boolean isMovie;
    private String releaseYear;
    private String thumbnail;
    private String title;
    private String genre;
}
