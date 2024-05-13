package com.klpc.stadspring.domain.advert.controller.response;

import com.klpc.stadspring.domain.advert.service.command.response.GetAdvertAdvertVideo;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Getter
@Builder
public class GetAdvertResponse {

    Long productId;
    String title;
    String description;
    String startDate;
    String endDate;
    String directVideoUrl;
    String bannerImgUrl;
    String type;
    List<Long> selectedContentList;
    List<GetAdvertAdvertVideo> advertVideoUrlList;
    String category;

}
