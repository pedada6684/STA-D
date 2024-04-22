package com.klpc.stadspring.domain.advertVideo.controller;

import com.klpc.stadspring.domain.advertVideo.controller.request.ModifyVideoRequest;
import com.klpc.stadspring.domain.advertVideo.controller.response.AddVideoListResponse;
import com.klpc.stadspring.domain.advertVideo.controller.response.DeleteResponse;
import com.klpc.stadspring.domain.advertVideo.controller.response.GetAdvertVideoResponse;
import com.klpc.stadspring.domain.advertVideo.controller.response.ModifyVideoResponse;
import com.klpc.stadspring.domain.advertVideo.service.AdvertVideoService;
import com.klpc.stadspring.domain.advertVideo.service.command.request.AddVideoListRequestCommand;
import com.klpc.stadspring.domain.advertVideo.service.command.request.ModifyVideoRequestCommand;
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

    @GetMapping("/spread-advert")
    public ResponseEntity<GetAdvertVideoResponse> getAdvertVideo(@RequestParam("advertVideoId") Long advertVideoId){
        GetAdvertVideoResponse response = advertVideoService.getAdvertVideo(advertVideoId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/modify-video")
    public ResponseEntity<ModifyVideoResponse> modifyVideo(@ModelAttribute ModifyVideoRequest request){
        ModifyVideoRequestCommand command = ModifyVideoRequestCommand.builder()
                .videoId(request.getVideoId())
                .video(request.getVideo())
                .build();

        ModifyVideoResponse response = advertVideoService.modifyVideo(command);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete-video")
    public ResponseEntity<DeleteResponse> deleteVideo(@RequestParam("advertVideoId") Long advertVideoId){
        DeleteResponse response = advertVideoService.deleteResponse(advertVideoId);

        return new ResponseEntity<>(response,HttpStatus.OK);
    }


}
