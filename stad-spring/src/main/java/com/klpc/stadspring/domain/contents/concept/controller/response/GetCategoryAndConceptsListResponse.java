package com.klpc.stadspring.domain.contents.concept.controller.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GetCategoryAndConceptsListResponse {
    List<GetCategoryAndConceptsResponse> categoryAndConceptsList;

    public static GetCategoryAndConceptsListResponse from(List<GetCategoryAndConceptsResponse> list) {
        return GetCategoryAndConceptsListResponse.builder()
                .categoryAndConceptsList(list)
                .build();
    }
}
