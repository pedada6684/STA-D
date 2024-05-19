package com.klpc.stadspring.domain.advert.service.command.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GetAdvertListByContentResponseCommand {

    Long advertId;
    String title;
    String description;
    String startDate;
    String endDate;
    String advertType;
    String directVideoUrl;
    String bannerImgUrl;
    List<Long> selectedContentList;
    List<String> advertVideoUrlList;
    String advertCategory;

}
