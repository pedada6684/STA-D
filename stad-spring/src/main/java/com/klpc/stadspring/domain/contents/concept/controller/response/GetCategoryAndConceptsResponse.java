package com.klpc.stadspring.domain.contents.concept.controller.response;

import com.klpc.stadspring.domain.contents.category.entity.ContentCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Builder
public class GetCategoryAndConceptsResponse {
    String category;
    List<GetConceptResponse> getConceptResponseList;

    public static GetCategoryAndConceptsResponse from(ContentCategory category, List<GetConceptResponse> list) {
        return GetCategoryAndConceptsResponse.builder()
                .category(category.getName())
                .getConceptResponseList(list)
                .build();
    }
}
