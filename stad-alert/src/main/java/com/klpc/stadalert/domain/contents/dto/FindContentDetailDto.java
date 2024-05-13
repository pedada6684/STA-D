package com.klpc.stadalert.domain.contents.dto;

import com.klpc.stadalert.domain.contents.entity.ContentDetail;
import lombok.Data;

@Data
public class FindContentDetailDto {
    private Long conceptId;
    private String title;
    private String thumbnailUrl;
    private String playtime;
    private String releaseYear;
    private String audienceAge;
    private String creator;
    private String cast;
    private String description;
}

