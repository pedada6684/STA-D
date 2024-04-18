package com.klpc.stadspring.domain.advertVideo.controller;

import com.klpc.stadspring.domain.advertVideo.controller.request.AddVideoListRequest;
import com.klpc.stadspring.domain.advertVideo.controller.response.AddVideoListResponse;
import com.klpc.stadspring.domain.advertVideo.service.AdvertVideoService;
import com.klpc.stadspring.domain.advertVideo.service.command.request.AddVideoListRequestCommand;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    public ResponseEntity<AddVideoListResponse> addVideoList(@RequestPart List<MultipartFile> videoList){
        AddVideoListResponse response = advertVideoService.addVideoList(AddVideoListRequestCommand.builder().list(videoList).build());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
