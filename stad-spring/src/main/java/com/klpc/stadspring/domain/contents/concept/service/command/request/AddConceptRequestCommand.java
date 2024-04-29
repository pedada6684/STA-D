package com.klpc.stadspring.domain.contents.concept.service.command.request;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Getter
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
    private List<String> genre;
}
