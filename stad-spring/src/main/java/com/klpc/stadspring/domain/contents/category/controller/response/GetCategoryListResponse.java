package com.klpc.stadspring.domain.contents.category.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Builder
public class GetCategoryListResponse {
    List<String> categoryList;

    public static GetCategoryListResponse from(List<String> list) {
        return GetCategoryListResponse.builder()
                .categoryList(list)
                .build();
    }
}
