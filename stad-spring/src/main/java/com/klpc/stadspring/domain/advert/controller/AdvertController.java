package com.klpc.stadspring.domain.advert.controller;

import com.klpc.stadspring.domain.advert.controller.request.AddAdvertRequest;
import com.klpc.stadspring.domain.advert.controller.request.ModifyAdvertRequest;
import com.klpc.stadspring.domain.advert.controller.response.*;
import com.klpc.stadspring.domain.advert.service.AdvertService;
import com.klpc.stadspring.domain.advert.service.command.request.AddAdvertRequestCommand;
import com.klpc.stadspring.domain.advert.service.command.request.ModifyAdvertRequestCommand;
import com.klpc.stadspring.domain.advertVideo.controller.response.ModifyVideoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(summary = "광고 등록", description = "광고 등록")
    @ApiResponse(responseCode = "200", description = "광고가 등록 되었습니다.")
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
    @Operation(summary = "광고 수정", description = "광고 수정")
    @ApiResponse(responseCode = "200", description = "광고가 수정 되었습니다.")
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
    @Operation(summary = "광고 삭제", description = "광고 삭제")
    @ApiResponse(responseCode = "200", description = "광고가 삭제 되었습니다.")
    public ResponseEntity<DeleteAdvertResponse> deleteAdvert(@RequestParam("advertId") Long advertId){
        DeleteAdvertResponse response = advertService.deleteAdvert(advertId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("get")
    @Operation(summary = "광고 조회", description = "광고 조회")
    @ApiResponse(responseCode = "200", description = "광고가 조회 되었습니다.")
    public ResponseEntity<GetAdvertResponse> getAdvert(@RequestParam("advertId") Long advertId){
        GetAdvertResponse response = advertService.getAdvert(advertId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("get-list")
    @Operation(summary = "광고 리스트 조회", description = "광고 리스트 조회")
    @ApiResponse(responseCode = "200", description = "광고 리스트가 조회 되었습니다.")
    public ResponseEntity<GetAdvertListResponse> getAdvertList(@RequestParam("userId") Long userId){
        GetAdvertListResponse response = advertService.getAdvertList(userId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
