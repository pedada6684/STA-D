package com.klpc.stadalert.domain.contents.entity;

import com.klpc.stadalert.domain.contents.dto.FindContentDetailDto;
import lombok.Data;

@Data
public class ContentDetail {
    private Long conceptId;
    private String title;
    private String thumbnailUrl;
    private String playtime;
    private String releaseYear;
    private String audienceAge;
    private String creator;
    private String cast;
    private String description;

    public static ContentDetail from(FindContentDetailDto dto){
        ContentDetail contentDetail = new ContentDetail();
        contentDetail.conceptId = dto.getConceptId();
        contentDetail.title =dto.getTitle();
        contentDetail.thumbnailUrl = dto.getThumbnailUrl();
        contentDetail.playtime = dto.getPlaytime();
        contentDetail.releaseYear = dto.getReleaseYear();
        contentDetail.audienceAge = dto.getAudienceAge();
        contentDetail.creator = dto.getCreator();
        contentDetail.cast = dto.getCast();
        contentDetail.description = dto.getDescription();
        return contentDetail;
    }
}
