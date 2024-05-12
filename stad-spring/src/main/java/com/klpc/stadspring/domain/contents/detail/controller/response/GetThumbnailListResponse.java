package com.klpc.stadspring.domain.contents.detail.controller.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
@Getter
@Builder
public class GetThumbnailListResponse {
    private List<GetThumbnailResponse> detailList;

    public static GetThumbnailListResponse from(List<GetThumbnailResponse> list) {
        return GetThumbnailListResponse.builder()
                .detailList(list)
                .build();
    }
}
