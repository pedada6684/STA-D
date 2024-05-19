package com.klpc.stadspring.domain.advert.controller.request;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class AddAdvertRequest {

    private Long userId;
    private String title;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String advertType;
    private String advertCategory;
    private String directVideoUrl;
    private String bannerImgUrl;
    private List<Long> selectedContentList;
    private List<String> advertVideoUrlList;

}
