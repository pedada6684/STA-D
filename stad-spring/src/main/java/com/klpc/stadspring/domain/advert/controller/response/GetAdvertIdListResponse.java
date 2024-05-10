package com.klpc.stadspring.domain.advert.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetAdvertIdListResponse {
    List<Long> advertIdList;
}
