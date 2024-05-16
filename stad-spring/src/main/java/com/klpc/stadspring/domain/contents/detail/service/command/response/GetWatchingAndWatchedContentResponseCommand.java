package com.klpc.stadspring.domain.contents.detail.service.command.response;

import com.klpc.stadspring.domain.contents.concept.entity.ContentConcept;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetWatchingAndWatchedContentResponseCommand {
    String title;
    String thumbnailUrl;
    Long conceptId;

    public static GetWatchingAndWatchedContentResponseCommand from(ContentConcept concept) {
        return GetWatchingAndWatchedContentResponseCommand.builder()
                .title(concept.getTitle())
                .thumbnailUrl(concept.getThumbnailUrl())
                .conceptId(concept.getId())
                .build();
    }
}
