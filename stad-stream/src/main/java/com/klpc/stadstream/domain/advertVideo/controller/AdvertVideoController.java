package com.klpc.stadstream.domain.advertVideo.controller;


import com.klpc.stadstream.domain.advertVideo.service.AdvertVideoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/advert-video")
@Tag(name = "advertVideo 컨트롤러", description = "광고 영상 API 입니다.")
public class AdvertVideoController {

    private final AdvertVideoService advertVideoService;

    @GetMapping("/{videoId}")
    @Operation(summary = "광고 스트리밍", description = "광고 스트리밍")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "광고 스트리밍 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 형식"),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류")
    })
    ResponseEntity<ResourceRegion> streamingPublicVideo(@RequestHeader HttpHeaders httpHeaders, @PathVariable Long videoId){
        log.info("광고 스트리밍" + "\n" + "streamingPublicVideo : "+videoId);

        return advertVideoService.streamingAdvertVideo(httpHeaders, videoId);
    }
}
