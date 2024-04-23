package com.klpc.stadspring.domain.advert.service.command.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GetAdvertResponseCommand {

    Long advertId;
    String title;
    String description;
    String startDate;
    String endDate;
    String type;
    String directVideoUrl;
    String bannerImgUrl;
    List<Long> selectedContentList;
    List<String> advertVideoUrlList;
    String category;

}
