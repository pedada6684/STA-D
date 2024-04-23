package com.klpc.stadspring.domain.advert.service.command.response;

import lombok.Builder;
import lombok.Getter;
import org.bytedeco.javacpp.annotation.ByPtr;

import java.util.List;

@Getter
@Builder
public class GetAdvertListByClickResponseCommand {

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
