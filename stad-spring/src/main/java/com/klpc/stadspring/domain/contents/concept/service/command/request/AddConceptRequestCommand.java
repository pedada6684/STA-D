package com.klpc.stadspring.domain.contents.concept.service.command.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddConceptRequestCommand {
    private String audienceAge;
    private String playtime;
    private String description;
    private String cast;
    private String creator;
    private boolean isMovie;
    private String releaseYear;
    private String thumbnail;
    private String title;
}
