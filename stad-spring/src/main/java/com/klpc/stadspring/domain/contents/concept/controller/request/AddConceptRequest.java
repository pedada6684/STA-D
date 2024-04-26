package com.klpc.stadspring.domain.contents.concept.controller.request;

import com.klpc.stadspring.domain.contents.concept.service.command.request.AddConceptRequestCommand;
import lombok.Getter;

@Getter
public class AddConceptRequest {
    private String audienceAge;
    private String playtime;
    private String description;
    private String cast;
    private String creator;
    // json 파싱할 때 주석 풀기
//    private String genre;
    private boolean isMovie;
    private String releaseYear;
    private String thumbnail;
    private String title;

    public AddConceptRequestCommand toCommand() {
        return AddConceptRequestCommand.builder()
                .audienceAge(audienceAge)
                .playtime(playtime)
                .description(description)
                .cast(cast)
                .creator(creator)
                .isMovie(isMovie)
                .releaseYear(releaseYear)
                .thumbnail(thumbnail)
                .title(title)
                .build();
    }
}
