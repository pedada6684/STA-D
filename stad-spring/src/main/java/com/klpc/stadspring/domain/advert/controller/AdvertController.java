package com.klpc.stadspring.domain.advert.controller;

import com.klpc.stadspring.domain.advert.controller.request.AddAdvertRequest;
import com.klpc.stadspring.domain.advert.controller.response.AddAdvertResponse;
import com.klpc.stadspring.domain.advert.service.AdvertService;
import com.klpc.stadspring.domain.advert.service.command.request.AddAdvertRequestCommand;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/advert")
@Tag(name = "advert 컨트롤러", description = "광고 API 입니다.")
public class AdvertController {

    private AdvertService advertService;

    @PostMapping
    public ResponseEntity<AddAdvertResponse> addAdvert(@RequestBody AddAdvertRequest request){
        AddAdvertRequestCommand command = AddAdvertRequestCommand.builder()
                .userId(request.getUserId())
                .title(request.getTitle())
                .description(request.getDescription())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .category(request.getCategory())
                .directVideoUrl(request.getDirectVideoUrl())
                .bannerImgUrl(request.getBannerImgUrl())
                .selectedContentList(request.getSelectedContentList())
                .advertVideoUrlList(request.getAdvertVideoUrlList())
                .advertBannerImgUrl(request.getBannerImgUrl())
                .build();
        AddAdvertResponse result = advertService.addAdvert(command);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
