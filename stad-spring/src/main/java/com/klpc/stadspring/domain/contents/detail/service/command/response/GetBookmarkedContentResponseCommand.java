package com.klpc.stadspring.domain.contents.detail.service.command.response;

import com.klpc.stadspring.domain.contents.concept.entity.ContentConcept;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetBookmarkedContentResponseCommand {
    String title;
    String thumbnailUrl;
    Long conceptId;

    public static GetBookmarkedContentResponseCommand from(ContentConcept concept) {
        return GetBookmarkedContentResponseCommand.builder()
                .title(concept.getTitle())
                .thumbnailUrl(concept.getThumbnailUrl())
                .conceptId(concept.getId())
                .build();
    }
}
