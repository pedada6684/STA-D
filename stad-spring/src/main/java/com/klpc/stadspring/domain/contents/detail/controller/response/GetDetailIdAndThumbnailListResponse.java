package com.klpc.stadspring.domain.contents.detail.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
@Getter
@Builder
public class GetDetailIdAndThumbnailListResponse {
    private List<GetDetailIdAndThumbnailResponse> detailList;

    public static GetDetailIdAndThumbnailListResponse from(List<GetDetailIdAndThumbnailResponse> list) {
        return GetDetailIdAndThumbnailListResponse.builder()
                .detailList(list)
                .build();
    }
}
