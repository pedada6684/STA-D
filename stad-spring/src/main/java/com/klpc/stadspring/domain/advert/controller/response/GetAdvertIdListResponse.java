package com.klpc.stadspring.domain.advert.controller.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GetAdvertIdListResponse {
    List<Long> advertIdList;
}
