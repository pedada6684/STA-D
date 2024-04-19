package com.klpc.stadspring.domain.advert.service.command.request;

import com.klpc.stadspring.domain.advert.entity.AdvertCategory;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class AddAdvertRequestCommand {

    private Long userId;
    private String title;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String category;
    private String directVideoUrl;
    private String bannerImgUrl;
    private List<Long> selectedContentList;
    private List<String> advertVideoUrlList;
    private String advertBannerImgUrl;

}
