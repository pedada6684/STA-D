package com.klpc.stadspring.domain.contents.concept.service.command.response;

import com.klpc.stadspring.domain.contents.concept.entity.ContentConcept;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetUpdatedContentResponseCommand {
    private String title;
    private String thumbnailUrl;
    private Long conceptId;

    public static GetUpdatedContentResponseCommand toCommand(ContentConcept contentConcept) {
        return GetUpdatedContentResponseCommand.builder()
                .title(contentConcept.getTitle())
                .thumbnailUrl(contentConcept.getThumbnailUrl())
                .conceptId(contentConcept.getId())
                .build();
    }
}
