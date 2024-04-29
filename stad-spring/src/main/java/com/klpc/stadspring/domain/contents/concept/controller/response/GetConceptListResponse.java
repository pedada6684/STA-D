package com.klpc.stadspring.domain.contents.concept.controller.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GetConceptListResponse {
    private List<GetConceptResponse> responseList;

    public static GetConceptListResponse from(List<GetConceptResponse> list) {
        return GetConceptListResponse.builder()
                .responseList(list)
                .build();
    }
}
