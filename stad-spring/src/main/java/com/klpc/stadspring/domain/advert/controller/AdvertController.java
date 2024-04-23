package com.klpc.stadspring.domain.advert.controller;

import com.klpc.stadspring.domain.advert.controller.request.AddAdvertRequest;
import com.klpc.stadspring.domain.advert.controller.request.ModifyAdvertRequest;
import com.klpc.stadspring.domain.advert.controller.response.AddAdvertResponse;
import com.klpc.stadspring.domain.advert.controller.response.DeleteAdvertResponse;
import com.klpc.stadspring.domain.advert.controller.response.ModifyAdvertResponse;
import com.klpc.stadspring.domain.advert.service.AdvertService;
import com.klpc.stadspring.domain.advert.service.command.request.AddAdvertRequestCommand;
import com.klpc.stadspring.domain.advert.service.command.request.ModifyAdvertRequestCommand;
import com.klpc.stadspring.domain.advertVideo.controller.response.ModifyVideoResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/advert")
@Tag(name = "advert 컨트롤러", description = "광고 API 입니다.")
public class AdvertController {

    private final AdvertService advertService;

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
        AddAdvertResponse response = advertService.addAdvert(command);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<ModifyAdvertResponse> modifyAdvert(@RequestBody ModifyAdvertRequest request){
        ModifyAdvertRequestCommand command = ModifyAdvertRequestCommand.builder()
                .advertId(request.getAdvertId())
                .title(request.getTitle())
                .description(request.getDescription())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .category(request.getCategory())
                .directVideoUrl(request.getDirectVideoUrl())
                .bannerImgUrl(request.getBannerImgUrl())
                .selectedContentList(request.getSelectedContentList())
                .advertBannerImgUrl(request.getBannerImgUrl())
                .build();
        ModifyAdvertResponse response = advertService.modifyAdvert(command);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<DeleteAdvertResponse> deleteAdvert(@RequestParam Long advertId){
        DeleteAdvertResponse response = advertService.deleteAdvert(advertId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
