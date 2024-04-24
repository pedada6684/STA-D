package com.klpc.stadspring.domain.contents.concept.controller.response;

import com.klpc.stadspring.domain.contents.category.entity.ContentCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class GetContentCategoryAndConceptListResponse {
    String category;
    List<GetConceptResponse> getConceptResponseList;

    public static GetContentCategoryAndConceptListResponse from(ContentCategory category, List<GetConceptResponse> list) {
        return GetContentCategoryAndConceptListResponse.builder()
                .category(category.getName())
                .getConceptResponseList(list)
                .build();
    }
}
