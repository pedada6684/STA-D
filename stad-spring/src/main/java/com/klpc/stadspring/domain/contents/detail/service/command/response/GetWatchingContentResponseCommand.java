package com.klpc.stadspring.domain.contents.detail.service.command.response;

import com.klpc.stadspring.domain.contents.concept.entity.ContentConcept;
import com.klpc.stadspring.domain.contents.detail.entity.ContentDetail;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetWatchingContentResponseCommand {
    String title;
    String thumbnailUrl;
    Integer episode;
    Long conceptId;

    public static GetWatchingContentResponseCommand from(ContentConcept concept, ContentDetail detail) {
        return GetWatchingContentResponseCommand.builder()
                .title(concept.getTitle())
                .thumbnailUrl(concept.getThumbnailUrl())
                .episode(detail.getEpisode())
                .conceptId(concept.getId())
                .build();
    }
}
