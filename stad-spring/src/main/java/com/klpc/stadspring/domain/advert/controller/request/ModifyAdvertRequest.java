package com.klpc.stadspring.domain.advert.controller.request;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ModifyAdvertRequest {

    private Long advertId;
    private String title;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String type;
    private String directVideoUrl;
    private String bannerImgUrl;
    private List<Long> selectedContentList;
    private String advertBannerImgUrl;

}
