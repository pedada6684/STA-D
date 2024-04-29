package com.klpc.stadspring.domain.contents.detail.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
@Getter
@AllArgsConstructor
@Builder
public class GetDetailIdAndThumbnailListResponse {
    private List<GetDetailIdAndThumbnailResponse> detailList;
}
