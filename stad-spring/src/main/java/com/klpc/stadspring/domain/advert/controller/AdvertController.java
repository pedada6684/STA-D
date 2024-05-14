package com.klpc.stadspring.domain.advert.controller;

import com.klpc.stadspring.domain.advert.controller.request.AddAdvertRequest;
import com.klpc.stadspring.domain.advert.controller.request.ModifyAdvertRequest;
import com.klpc.stadspring.domain.advert.controller.response.*;
import com.klpc.stadspring.domain.advert.service.AdvertService;
import com.klpc.stadspring.domain.advert.service.command.request.AddAdvertRequestCommand;
import com.klpc.stadspring.domain.advert.service.command.request.ModifyAdvertRequestCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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
        log.info("광고 등록 Controller"+"\n"+"title : "+request.getTitle());
        log.info("directVideoUrl : "+request.getDirectVideoUrl());
        AddAdvertRequestCommand command = AddAdvertRequestCommand.builder()
                .userId(request.getUserId())
                .title(request.getTitle())
                .description(request.getDescription())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .advertType(request.getAdvertType())
                .directVideoUrl(request.getDirectVideoUrl())
                .bannerImgUrl(request.getBannerImgUrl())
                .selectedContentList(request.getSelectedContentList())
                .advertVideoUrlList(request.getAdvertVideoUrlList())
                .advertCategory(request.getAdvertCategory())
                .build();
        AddAdvertResponse response = advertService.addAdvert(command);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping
    @Operation(summary = "광고 수정", description = "광고 수정")
    @ApiResponse(responseCode = "200", description = "광고가 수정 되었습니다.")
    public ResponseEntity<ModifyAdvertResponse> modifyAdvert(@RequestBody ModifyAdvertRequest request){
        log.info("광고 수정 Controller"+"\n"+"title : "+request.getTitle());
        ModifyAdvertRequestCommand command = ModifyAdvertRequestCommand.builder()
                .advertId(request.getAdvertId())
                .title(request.getTitle())
                .description(request.getDescription())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .advertType(request.getAdvertType())
                .advertCategory(request.getAdvertCategory())
                .directVideoUrl(request.getDirectVideoUrl())
                .bannerImgUrl(request.getBannerImgUrl())
                .selectedContentList(request.getSelectedContentList())
                .advertVideoUrlList(request.getAdvertVideoUrlList())
                .build();
        ModifyAdvertResponse response = advertService.modifyAdvert(command);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping
    @Operation(summary = "광고 삭제", description = "광고 삭제")
    @ApiResponse(responseCode = "200", description = "광고가 삭제 되었습니다.")
    public ResponseEntity<DeleteAdvertResponse> deleteAdvert(@RequestParam("advertId") Long advertId){
        log.info("광고 삭제 Controller"+"\n"+"advertId : "+advertId);
        DeleteAdvertResponse response = advertService.deleteAdvert(advertId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("get")
    @Operation(summary = "광고 조회", description = "광고 조회")
    @ApiResponse(responseCode = "200", description = "광고가 조회 되었습니다.")
    public ResponseEntity<GetAdvertResponse> getAdvert(@RequestParam("advertIds") List<Long> advertIds){
        log.info("광고 조회 Controller"+"\n"+"advertId : ");
        for(Long advertId : advertIds) {
            log.info(advertId+" ");
        }
        GetAdvertResponse response = advertService.getAdvert(advertIds);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("get-list")
    @Operation(summary = "광고 리스트 조회", description = "광고 리스트 조회")
    @ApiResponse(responseCode = "200", description = "광고 리스트가 조회 되었습니다.")
    public ResponseEntity<GetAdvertListResponse> getAdvertList(@RequestParam("userId") Long userId){
        log.info("광고 리스트 조회 Controller"+"\n"+"userId : "+userId);
        GetAdvertListResponse response = advertService.getAdvertList(userId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("get-list-by-content")
    @Operation(summary = "컨텐츠 관련 광고 리스트 조회", description = "컨텐츠 별 광고 리스트 조회")
    @ApiResponse(responseCode = "200", description = "광고 리스트가 조회 되었습니다.")
    public ResponseEntity<GetAdvertListByContentResponse> getAdvertListByContent(@RequestParam("contentId") Long contentId){
        log.info("컨텐츠 관련 광고 리스트 조회 Controller"+"\n"+"contentId : "+contentId);
        GetAdvertListByContentResponse response = advertService.getAdvertListByContent(contentId);

        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("get-list-by-click")
    @Operation(summary = "인기 광고 리스트 조회", description = "인기 광고 리스트 조회")
    @ApiResponse(responseCode = "200", description = "광고 리스트가 조회 되었습니다.")
    public ResponseEntity<GetAdvertListByClickResponse> getAdvertListByClick(){
        log.info("인기 광고 리스트 조회 Controller"+"\n");

        GetAdvertListByClickResponse response = advertService.getAdvertListByClick();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("get-advert-id-list")
    @Operation(summary = "광고 아이디 리스트 조회", description = "광고 아이디 리스트 조회")
    @ApiResponse(responseCode = "200", description = "광고 아이디 리스트가 조회 되었습니다.")
    public ResponseEntity<GetAdvertIdListResponse> getAdvertIdList(){
        GetAdvertIdListResponse response = advertService.GetAdvertIdList();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("get-user-advert")
    @Operation(summary = "유저가 본 광고 조회", description = "유저가 본 광고 조회")
    @ApiResponse(responseCode = "200", description = "유저가 본 광고가 조회 되었습니다.")
    public ResponseEntity<GetAdvertListResponse> getAdvertListByUser(Long userId){

        GetAdvertListResponse response = advertService.GetAdvertById(userId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
