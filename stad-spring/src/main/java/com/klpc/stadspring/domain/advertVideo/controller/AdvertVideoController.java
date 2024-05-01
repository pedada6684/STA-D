package com.klpc.stadspring.domain.advertVideo.controller;

import com.klpc.stadspring.domain.advertVideo.controller.request.ModifyVideoRequest;
import com.klpc.stadspring.domain.advertVideo.controller.response.*;
import com.klpc.stadspring.domain.advertVideo.entity.AdvertVideo;
import com.klpc.stadspring.domain.advertVideo.service.AdvertVideoService;
import com.klpc.stadspring.domain.advertVideo.service.command.request.AddBannerImgRequestCommand;
import com.klpc.stadspring.domain.advertVideo.service.command.request.AddVideoListRequestCommand;
import com.klpc.stadspring.domain.advertVideo.service.command.request.ModifyVideoRequestCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/advert-video")
@Tag(name = "advertVideo 컨트롤러", description = "광고 영상 API 입니다.")
public class AdvertVideoController {

    private final AdvertVideoService advertVideoService;

    @PostMapping("/add-video-list")
    @Operation(summary = "광고 영상 업로드", description = "광고 영상 업로드")
    @ApiResponse(responseCode = "200", description = "광고 영상이 업로드 되었습니다.")
    public ResponseEntity<AddVideoListResponse> addVideoList(@RequestPart("videoList") List<MultipartFile> videoList){
        log.info("광고 영상 업로드"+"\n"+"videoList Size : "+videoList.size());
        AddVideoListResponse response = advertVideoService.addVideoList(AddVideoListRequestCommand.builder().list(videoList).build());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/spread-advert")
    @Operation(summary = "광고 영상 조회", description = "광고 영상 조회")
    @ApiResponse(responseCode = "200", description = "광고 영상이 조회 되었습니다.")
    public ResponseEntity<GetAdvertVideoResponse> getAdvertVideo(@RequestParam("advertVideoId") Long advertVideoId){
        GetAdvertVideoResponse response = advertVideoService.getAdvertVideo(advertVideoId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/modify-video")
    @Operation(summary = "광고 영상 수정", description = "광고 영상 수정")
    @ApiResponse(responseCode = "200", description = "광고 영상이 수정 되었습니다.")
    public ResponseEntity<ModifyVideoResponse> modifyVideo(@ModelAttribute ModifyVideoRequest request){
        ModifyVideoRequestCommand command = ModifyVideoRequestCommand.builder()
                .videoId(request.getVideoId())
                .video(request.getVideo())
                .build();

        ModifyVideoResponse response = advertVideoService.modifyVideo(command);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete-video")
    @Operation(summary = "광고 영상 삭제", description = "광고 영상 삭제")
    @ApiResponse(responseCode = "200", description = "광고 영상이 삭제 되었습니다.")
    public ResponseEntity<DeleteResponse> deleteVideo(@RequestParam("advertVideoId") Long advertVideoId){
        DeleteResponse response = advertVideoService.deleteVideo(advertVideoId);

        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/add-banner")
    @Operation(summary = "광고 배너 이미지 업로드", description = "광고 배너 이미지 업로드")
    @ApiResponse(responseCode = "200", description = "광고 배너 이미지가 업로드 되었습니다.")
    public ResponseEntity<AddBannerImgResponse> addBannerImg(@RequestPart("bannerImg") MultipartFile bannerImg){
        log.info("광고 배너 이미지 업로드"+"\n"+"bannerImgName : "+bannerImg.getName());
        AddBannerImgRequestCommand command = AddBannerImgRequestCommand.builder().img(bannerImg).build();

        AddBannerImgResponse response = advertVideoService.addBannerImg(command);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/get-list-by-user")
    @Operation(summary = "사용자 맞춤 광고 조회", description = "사용자 맞춤 광고 조회")
    @ApiResponse(responseCode = "200", description = "사용자 맞춤 광고 영상이 조회 되었습니다.")
    public ResponseEntity<GetAdvertVideoListByUserResponse> getAdvertVideoByUser(@RequestParam("userId") Long userId){
        GetAdvertVideoListByUserResponse response = advertVideoService.getAdvertVideoByUser(userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
