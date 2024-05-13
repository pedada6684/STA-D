package com.klpc.stadspring.domain.advertVideo.controller.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GetFinalAdvertVideoListResponse {
    List<String> data;
    List<Long> advertVideoIdList;
}
